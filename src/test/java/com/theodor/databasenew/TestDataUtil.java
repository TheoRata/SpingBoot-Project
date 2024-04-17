package com.theodor.databasenew;

import org.junit.jupiter.api.Test;

import com.theodor.databasenew.domain.Author;
import com.theodor.databasenew.domain.Book;

public final class TestDataUtil {

    private TestDataUtil(){
    }

    public static Author createTestAutorA() {
        Author author = Author.builder()
                .id(1L)
                .name("Rata Theodor")
                .age(23)
                .build();
        return author;
    }
    public static Author createTestAutorB() {
        Author author = Author.builder()
                .id(2L)
                .name("Andrei Rata")
                .age(11)
                .build();
        return author;
    }
    public static Author createTestAutorC() {
        Author author = Author.builder()
                .id(3L)
                .name("Mihai Alexandru")
                .age(52)
                .build();
        return author;
    }

    public static Book createTestBookA() {
        return Book.builder()
                .isbn("1234-2345-234-0")
                .title("Bucuresti")
                .authorId(1L)
                .build();
    }
    public static Book createTestBookB() {
        return Book.builder()
                .isbn("6789-9078-55-1")
                .title("Braila")
                .authorId(1L)
                .build();
    }
    public static Book createTestBookC() {
        return Book.builder()
                .isbn("99-987-678-2")
                .title("Galati")
                .authorId(1L)
                .build();
    }


    
}
