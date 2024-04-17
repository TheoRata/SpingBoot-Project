package com.theodor.databasenew;

import org.junit.jupiter.api.Test;

import com.theodor.databasenew.domain.Author;

public final class TestDataUtil {

    private TestDataUtil(){
    }

    public static Author createTestAutor() {
        Author author = Author.builder()
                .id(1L)
                .name("Rata Theodor")
                .age(23)
                .build();
        return author;
    }


    
}
