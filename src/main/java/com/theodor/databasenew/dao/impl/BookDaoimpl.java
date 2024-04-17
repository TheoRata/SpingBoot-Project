package com.theodor.databasenew.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.theodor.databasenew.domain.Book;
import com.theodor.databasenew.dao.BookDao;

@Component
public class BookDaoimpl implements BookDao{
    
    private final JdbcTemplate jdbcTemplate;

    public BookDaoimpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

   @Override
   public void create(Book book){

    jdbcTemplate.update(
        "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)", 
        book.getIsbn(),
        book.getTitle(),
        book.getAuthorId()
        );
   }

    @Override
    public Optional<Book> findOne(String isbn) {
       
        List<Book> results = jdbcTemplate.query(
            "SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1", 
            new BookRowMapper(), 
            isbn
            );

        return results.stream().findFirst();

    }

    public static class BookRowMapper implements RowMapper<Book>{

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException{
            return Book.builder()
                .isbn(rs.getString("isbn"))
                .title(rs.getString("title"))
                .authorId(rs.getLong("author_id"))
                .build();
        }

    }

    @Override
    public List<Book> find() {
        return jdbcTemplate.query("SELECT isbn, title, author_id FROM books", 
        new BookRowMapper()
        );
    }


    
   
      
}
