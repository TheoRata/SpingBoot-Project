package com.theodor.databasenew;



import com.theodor.databasenew.domain.dto.AuthorDto;
import com.theodor.databasenew.domain.dto.BookDto;
import com.theodor.databasenew.domain.entities.AuthorEntity;
import com.theodor.databasenew.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil(){
    }

    public static AuthorEntity createTestAutorA() {
        AuthorEntity author = AuthorEntity.builder()
                .id(1L)
                .name("Rata Theodor")
                .age(23)
                .build();
        return author;
    }
    public static AuthorEntity createTestAutorB() {
        AuthorEntity author = AuthorEntity.builder()
                .id(2L)
                .name("Andrei Rata")
                .age(11)
                .build();
        return author;
    }
    public static AuthorEntity createTestAutorC() {
        AuthorEntity author = AuthorEntity.builder()
                .id(3L)
                .name("Mihai Alexandru")
                .age(52)
                .build();
        return author;
    }
    public static AuthorDto createTestAutorDtoA() {
        AuthorDto author = AuthorDto.builder()
                .id(3L)
                .name("Mihai Alexandru")
                .age(52)
                .build();
        return author;
    }

    public static BookEntity createTestBookA(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("1234-2345-234-0")
                .title("Bucuresti")
                .author(author)
                .build();
    }
    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return BookDto.builder()
                .isbn("1234-2345-234-0")
                .title("Bucuresti")
                .author(author)
                .build();
    }
    public static BookEntity createTestBookB(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("6789-9078-55-1")
                .title("Braila")
                .author(author)
                .build();
    }
    public static BookEntity createTestBookC(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("99-987-678-2")
                .title("Galati")
                .author(author)
                .build();
    }


    
}
