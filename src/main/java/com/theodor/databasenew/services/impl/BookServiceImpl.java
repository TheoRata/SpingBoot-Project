package com.theodor.databasenew.services.impl;

import org.springframework.stereotype.Service;

import com.theodor.databasenew.domain.entities.BookEntity;
import com.theodor.databasenew.repositories.BookRepository;
import com.theodor.databasenew.services.BookService;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    


    
}
