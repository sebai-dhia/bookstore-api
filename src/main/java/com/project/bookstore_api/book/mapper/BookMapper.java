package com.project.bookstore_api.book.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.project.bookstore_api.book.Book;
import com.project.bookstore_api.book.dto.BookRequestDto;
import com.project.bookstore_api.book.dto.BookResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponseDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toEntity(BookRequestDto bookRequestDto);

    @Mapping(target = "id" ,ignore = true)
    void updateEntity(BookRequestDto bookRequestDto, @MappingTarget Book book);

    List<BookResponseDto> toDtoList(List<Book> books);
    List<Book> toEntityList(List<BookRequestDto> bookRequestDtos);
}
