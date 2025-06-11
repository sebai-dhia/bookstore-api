package com.project.bookstore_api.features.checkout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.bookstore_api.features.book.exception.OutOfStockException;
import com.project.bookstore_api.features.payment.PaymentRepository;
import com.project.bookstore_api.features.payment.model.Payment;
import com.project.bookstore_api.features.payment.model.PaymentMethod;
import com.project.bookstore_api.features.payment.model.PaymentStatus;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookstore_api.features.basket.BasketRepository;
import com.project.bookstore_api.features.basket.model.Basket;
import com.project.bookstore_api.features.basket.model.BasketItem;
import com.project.bookstore_api.features.book.Book;
import com.project.bookstore_api.features.checkout.dto.CheckoutItemResponse;
import com.project.bookstore_api.features.checkout.dto.CheckoutResponse;
import com.project.bookstore_api.features.order.OrderRepository;
import com.project.bookstore_api.features.order.model.Order;
import com.project.bookstore_api.features.order.model.OrderItem;
import com.project.bookstore_api.features.order.model.OrderStatus;
import com.project.bookstore_api.features.user.repository.CustomerRepository;
import com.project.bookstore_api.features.user.model.Customer;
import com.project.bookstore_api.features.user.model.Guest;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService{
    final private BasketRepository basketRepository;
    final private CustomerRepository customerRepository;
    final private OrderRepository orderRepository;
    final private PaymentRepository paymentRepository;

    @Transactional
    @Override
    public CheckoutResponse checkout(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));

        Basket basket = basketRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Basket has issue"));

        if (customer instanceof Guest guest) {
            if (guest.getEmail() == null || guest.getAddress() == null) {
                throw new RuntimeException("Email or Address must be not null");
            }
        }

        return processCheckout(customer, basket);
    }


    private CheckoutResponse processCheckout(Customer customer, Basket basket) {
        if (basket.getItems().isEmpty()) {
            throw new RuntimeException("Basket is Empty");
        }

        for (BasketItem item : basket.getItems()) {
            Book book = item.getBook();
            if (book.getStock() < item.getQuantity()) {
                throw new OutOfStockException(
                        book.getId(),
                        book.getTitle(),
                        book.getStock(),
                        item.getQuantity());
            }
        }

        List<OrderItem> items = new ArrayList<>();

        for (BasketItem basketItem : basket.getItems()) {
            OrderItem item = OrderItem.builder()
                    .book(basketItem.getBook())
                    .quantity(basketItem.getQuantity())
                    .priceAtPurchase(basketItem.getPriceAtPurchase())
                    .build();
            items.add(item);
        }

        Order order = Order.builder()
                .customer(customer)
                .status(OrderStatus.AWAITING_PAYMENT)
                .dateCreated(LocalDateTime.now())
                .items(items)
                .build();

        items.forEach(item -> item.setOrder(order));

        var payment = Payment.builder()
                        .amount(order.getTotalPrice())
                        .createdAt(LocalDateTime.now())
                        .method(PaymentMethod.CASH)
                        .status(PaymentStatus.PENDING)
                        .order(order)
                        .build();


        basket.getItems().forEach(item -> item.setBasket(null));
        basket.getItems().clear();

        List<CheckoutItemResponse> checkoutItems = items.stream()
                .map(item -> new CheckoutItemResponse(
                        item.getBook().getId(),
                        item.getQuantity(),
                        item.getPriceAtPurchase()))
                .toList();

        orderRepository.save(order);
        paymentRepository.save(payment);
        basketRepository.saveAndFlush(basket);

        return new CheckoutResponse(
                order.getId(),
                customer.getId(),
                checkoutItems,
                order.getStatus(),
                order.getDateCreated()
        );
    }
}
