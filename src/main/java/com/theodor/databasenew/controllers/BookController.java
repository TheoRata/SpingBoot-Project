package com.theodor.databasenew.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.theodor.databasenew.domain.dto.BookDto;
import com.theodor.databasenew.domain.entities.BookEntity;
import com.theodor.databasenew.mappers.Mapper;
import com.theodor.databasenew.services.BookService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class BookController {
    
    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;
    
    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService){
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        BookDto savedUpdatedBookDto = bookMapper.mapTo(savedBookEntity);
        if(bookExists){
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.CREATED);
        }
        
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAll(); 
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList()
                
            );
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> foundBook = bookService.findOne(isbn);
        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
}
