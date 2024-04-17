package com.theodor.databasenew.dao.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.theodor.databasenew.dao.impl.BookDaoimpl;
import com.theodor.databasenew.domain.Book;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {
    
    @InjectMocks
    private BookDaoimpl underTest;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testCreateBookGeneratesCorrectSQL() {

        Book book = Book.builder()
                .isbn("1234-2345-234-0")
                .title("Bucuresti")
                .authorId(1L)
                .build();

        underTest.create(book);

        verify(jdbcTemplate).update(
            eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"), 
            eq("1234-2345-234-0"), eq("Bucuresti"), eq(1L)
        );
    }

    @Test
    public void testFindOneGeneratesCorrectBookSQL(){

        underTest.find("1234-2345-234-0");

        verify(jdbcTemplate).query(
            eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
            ArgumentMatchers.<BookDaoimpl.BookRowMapper>any(),
            eq("1234-2345-234-0")
        );
    }
}
