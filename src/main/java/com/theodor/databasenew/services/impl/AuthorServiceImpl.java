package com.theodor.databasenew.services.impl;

import org.springframework.stereotype.Service;

import com.theodor.databasenew.domain.entities.AuthorEntity;
import com.theodor.databasenew.repositories.AuthorRepository;
import com.theodor.databasenew.services.AuthorService;

@Service
public class AuthorServiceImpl  implements AuthorService{
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
        
    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    
    }
    
}
