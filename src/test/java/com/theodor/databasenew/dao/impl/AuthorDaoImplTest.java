package com.theodor.databasenew.dao.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import javax.swing.tree.RowMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.theodor.databasenew.TestDataUtil;
import com.theodor.databasenew.dao.impl.AuthorDaoImpl;
import com.theodor.databasenew.domain.Author;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testCreateAuthorGeneratesCorrectSQL(){
        Author author = TestDataUtil.createTestAutor();

        underTest.create(author);

        verify(jdbcTemplate).update(
            eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
            eq(1L), eq("Rata Theodor"), eq(23)
        );
    }

    @Test
    public void testFindOneGeneratesCorrectSQL(){
        underTest.findOne(1L);

        verify(jdbcTemplate).query(
            eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"), 
            ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
            eq(1L));
    }

}
