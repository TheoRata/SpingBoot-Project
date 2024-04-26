package com.theodor.databasenew.services;

import java.util.List;
import java.util.Optional;

import com.theodor.databasenew.domain.entities.BookEntity;

public interface BookService {

    BookEntity createUpdateBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);

    void delete(String isbn);
    
}
