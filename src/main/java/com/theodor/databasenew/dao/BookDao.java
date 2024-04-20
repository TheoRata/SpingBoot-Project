package com.theodor.databasenew.dao;

import java.util.List;
import java.util.Optional;

import com.theodor.databasenew.domain.Book;

public interface BookDao {

    public void create(Book book);

    Optional<Book> findOne(String isbn);

    List<Book> find();

    public void update(String isbn, Book book);

    public void delete(String isbn);
    


}
