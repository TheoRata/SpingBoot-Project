package com.theodor.databasenew.dao;

import java.util.Optional;

import com.theodor.databasenew.domain.Book;

public interface BookDao {

    public void create(Book book);

    Optional<Book> find(String isbn);
    


}
