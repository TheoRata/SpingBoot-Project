package com.theodor.databasenew.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.theodor.databasenew.domain.entities.BookEntity;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String>{

    
    
}
