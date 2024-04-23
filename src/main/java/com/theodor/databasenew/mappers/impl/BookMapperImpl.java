package com.theodor.databasenew.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.theodor.databasenew.domain.dto.BookDto;
import com.theodor.databasenew.domain.entities.BookEntity;
import com.theodor.databasenew.mappers.Mapper;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto>{

    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        
        return modelMapper.map(bookDto, BookEntity.class);
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        
        return modelMapper.map(bookEntity, BookDto.class);
    }

    
    
}
