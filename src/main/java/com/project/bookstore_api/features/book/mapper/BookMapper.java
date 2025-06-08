package com.project.bookstore_api.features.book.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.project.bookstore_api.features.book.Book;
import com.project.bookstore_api.features.book.dto.BookRequestDto;
import com.project.bookstore_api.features.book.dto.BookResponseDto;

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
