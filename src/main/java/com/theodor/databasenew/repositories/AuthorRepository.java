package com.theodor.databasenew.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.theodor.databasenew.domain.entities.AuthorEntity;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long>{
    
    Iterable<AuthorEntity> ageLessThan(int age);
    
}
