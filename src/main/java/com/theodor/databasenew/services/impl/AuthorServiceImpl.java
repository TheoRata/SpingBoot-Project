package com.theodor.databasenew.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);

    }

    @Override
    public List<AuthorEntity> findAll() {

        /*This converts the Iterable into a Stream. 
        The spliterator method returns a Spliterator over the elements in the Iterable, 
        and StreamSupport.stream creates a sequential or parallel Stream from the Spliterator.
        The second argument to StreamSupport.stream is a boolean that controls whether the stream should be parallel;
        in this case, it's false, so a sequential stream is created. */
        return StreamSupport.stream(authorRepository
                     .findAll()
                     .spliterator(), 
                     false)
                     .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    
    
    
    
}
