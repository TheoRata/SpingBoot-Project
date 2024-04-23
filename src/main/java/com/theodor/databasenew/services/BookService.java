package com.theodor.databasenew.services;

import com.theodor.databasenew.domain.entities.BookEntity;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity bookEntity);
    
}
