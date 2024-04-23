package com.theodor.databasenew.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.theodor.databasenew.domain.dto.AuthorDto;
import com.theodor.databasenew.domain.entities.AuthorEntity;
import com.theodor.databasenew.services.AuthorService;

import com.theodor.databasenew.mappers.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthorController {
    
    private AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDto> authorMapper;
    
    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }


    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {

        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);

    }
    
}
