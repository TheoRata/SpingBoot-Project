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

import com.theodor.databasenew.TestDataUtil;
import com.theodor.databasenew.dao.impl.BookDaoimpl;
import com.theodor.databasenew.domain.Book;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {
    
    @InjectMocks
    private BookDaoimpl underTest;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testCreateBookGeneratesCorrectSql() {

        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
            eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"), 
            eq("1234-2345-234-0"), eq("Bucuresti"), eq(1L)
        );
    }

    @Test
    public void testFindOneGeneratesCorrectBookSql(){

        underTest.findOne("1234-2345-234-0");

        verify(jdbcTemplate).query(
            eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
            ArgumentMatchers.<BookDaoimpl.BookRowMapper>any(),
            eq("1234-2345-234-0")
        );
    }
    
    @Test
    public void testThatFindGeneratesCorrectSql(){

        underTest.find();
        verify(jdbcTemplate).query(
            eq("SELECT isbn, title, author_id FROM books"),
            ArgumentMatchers.<BookDaoimpl.BookRowMapper>any()
            );

    }

    @Test
    public void testUpdateBookGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();
        underTest.update("1234-2345-234-0",book);

        verify(jdbcTemplate).update(
            "UPDATE books SET isbn = ?, title = ?, author_id = ?  WHERE isbn = ?", 
            "1234-2345-234-0", "Bucuresti", 1L, "1234-2345-234-0"  
            );
    }

    @Test
    public void testDeleteBookGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();
        underTest.delete("1234-2345-234-0");

        verify(jdbcTemplate).update(
            "DELETE FROM books WHERE isbn = ?", 
            "1234-2345-234-0"
            );
    }
}
