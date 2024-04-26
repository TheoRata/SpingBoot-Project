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
import org.springframework.web.bind.annotation.DeleteMapping;
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

    /**
     * Creates or updates a book.
     *
     * @param  isbn    the ISBN of the book
     * @param  bookDto the book data transfer object
     * @return         the response entity containing the updated book DTO and the HTTP status code
     */
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

    /**
     * Retrieves a list of BookDto objects representing all books in the database.
     *
     * @return         	A List of BookDto objects representing all books in the database.
     */
    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAll(); 
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList()
                
            );
    }

    /**
     * Retrieves a book by its ISBN and returns it as a ResponseEntity containing a BookDto.
     *
     * @param  isbn    the ISBN of the book to retrieve
     * @return         a ResponseEntity containing the BookDto if found, or a ResponseEntity with
     *                 HttpStatus.NOT_FOUND if not found
     */
    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> foundBook = bookService.findOne(isbn);
        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable("isbn") String isbn){
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
