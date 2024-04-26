package com.theodor.databasenew.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.theodor.databasenew.domain.dto.AuthorDto;
import com.theodor.databasenew.domain.entities.AuthorEntity;
import com.theodor.databasenew.services.AuthorService;

import com.theodor.databasenew.mappers.Mapper;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PutMapping;




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
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);

    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors() {
         List<AuthorEntity> authors = authorService.findAll();
         return authors.stream() //This converts the authors list into a Stream. A Stream is a sequence of elements that can be processed in parallel or sequentially.
            .map(authorMapper::mapTo) //This applies the mapTo method of authorMapper to each element in the Stream.
            .collect(Collectors.toList()); //This collects the elements of the Stream into a new list.
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);

        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        authorMapper.mapTo(savedAuthorEntity);
        return new ResponseEntity<>(
            authorMapper.mapTo(savedAuthorEntity),
            HttpStatus.OK
        );
    }

    
    
    
    
}
