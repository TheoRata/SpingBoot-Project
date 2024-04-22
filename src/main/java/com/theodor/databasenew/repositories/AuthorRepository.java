package com.theodor.databasenew.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.theodor.databasenew.domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>{
    
    Iterable<Author> ageLessThan(int age);
    
}
