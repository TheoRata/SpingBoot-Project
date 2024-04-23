package com.theodor.databasenew.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.theodor.databasenew.domain.dto.BookDto;
import com.theodor.databasenew.domain.entities.BookEntity;
import com.theodor.databasenew.mappers.Mapper;
import com.theodor.databasenew.services.BookService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class BookController {
    
    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;
    
    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService){
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }


    @PutMapping("books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBook(isbn, bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);

        
        
    }
}
