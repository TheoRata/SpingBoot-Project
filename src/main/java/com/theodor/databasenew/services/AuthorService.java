package com.theodor.databasenew.services;

import java.util.List;
import java.util.Optional;

import com.theodor.databasenew.domain.entities.AuthorEntity;

public interface AuthorService {
    
    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id);
}
