package com.theodor.databasenew.dao.impl;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import com.theodor.databasenew.TestDataUtil;
import com.theodor.databasenew.domain.Author;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testCreateAuthorGeneratesCorrectSql(){
        Author author = TestDataUtil.createTestAutorA();

        underTest.create(author);

        verify(jdbcTemplate).update(
            eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
            eq(1L), eq("Rata Theodor"), eq(23)
        );
    }

    @Test
    public void testFindOneGeneratesCorrectSql(){
        underTest.findOne(1L);

        verify(jdbcTemplate).query(
            eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"), 
            ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
            eq(1L));
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(
            eq("SELECT id, name, age FROM authors"), 
            ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );

    }

    @Test
    public void testUpdateAuthorGeneratesCorrectSql(){
        Author author = TestDataUtil.createTestAutorA();
        underTest.update(3L, author);

        verify(jdbcTemplate).update(
            "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?", 
            1L,"Rata Theodor",23,3L
        );
    }

    @Test
    public void testThatAuthorDeleteGeneratesTheCorectSql(){
        underTest.delete(1L);

        verify(jdbcTemplate).update(
            "DELETE FROM authors where id = ?",
            1L);
        
    }

}
