package com.theodor.databasenew.dao;

import java.util.List;
import java.util.Optional;

import com.theodor.databasenew.domain.Author;

public interface AuthorDao {
    public void create(Author author);

    Optional<Author> findOne(long l);

    List<Author> find();

    void update(long id, Author author);

    void delete(long id);
}
