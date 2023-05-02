package com.microservice.library.service;

import com.microservice.library.dto.BookQueryResponse;
import com.microservice.library.dto.NewBookRequest;
import com.microservice.library.dto.NewBookResponse;
import com.microservice.library.model.Book;
import com.microservice.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public NewBookResponse addNewBook(NewBookRequest request) {
        Book book = Book.builder()
                .name(request.getName())
                .level(request.getLevel())
                .author(request.getAuthor())
                .build();

        bookRepository.save(book);

        return new NewBookResponse("new book added successfully");
    }

    public List<BookQueryResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream().map(this::mapToBookResponse).collect(Collectors.toList());
    }

    public BookQueryResponse getBookById(int id) {
        return mapToBookResponse(bookRepository.findById(id).get());
    }

    private BookQueryResponse mapToBookResponse(Book book) {
        return BookQueryResponse.builder()
                .author(book.getAuthor())
                .level(book.getLevel())
                .name(book.getName())
                .id(book.getId())
                .build();
    }
}
