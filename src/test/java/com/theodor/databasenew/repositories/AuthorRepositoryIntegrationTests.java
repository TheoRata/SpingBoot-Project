package com.theodor.databasenew.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.theodor.databasenew.TestDataUtil;
import com.theodor.databasenew.domain.Author;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) { 
        this.underTest = underTest;
    }


    @Test
    public void testAuthorCanBeCreatedAndRecalled(){
        
        Author author = TestDataUtil.createTestAutorB();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);

    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        Author authorA = TestDataUtil.createTestAutorA();
        Author authorB = TestDataUtil.createTestAutorB();
        Author authorC = TestDataUtil.createTestAutorC();
       
        
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);
        
        

        Iterable<Author> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(authorA, authorB, authorC);
    }
    
    @Test
    public void testThatAuthorCanBeUpdated(){
        Author authorA = TestDataUtil.createTestAutorA();
        underTest.save(authorA);
        authorA.setName("MIAU"); 
        underTest.save(authorA);
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        Author authorA = TestDataUtil.createTestAutorA();
        underTest.save(authorA);
        underTest.deleteById(authorA.getId());
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isEmpty();

    }

    @Test
    public void testAuthorWithAgeLessThan(){
        Author authorA = TestDataUtil.createTestAutorA();
        underTest.save(authorA);
        Author authorB = TestDataUtil.createTestAutorB();
        underTest.save(authorB);
        Author authorC = TestDataUtil.createTestAutorC();
        underTest.save(authorC);

        Iterable<Author> results = underTest.ageLessThan(50);
        assertThat(results).containsExactly(authorA, authorB);

    }
}
