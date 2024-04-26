package com.theodor.databasenew.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodor.databasenew.TestDataUtil;
import com.theodor.databasenew.domain.dto.AuthorDto;
import com.theodor.databasenew.domain.entities.AuthorEntity;
import com.theodor.databasenew.services.AuthorService;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateAuthorSuccesfullyReturns200HttpCreated() throws Exception{
        AuthorEntity testAuthorA = TestDataUtil.createTestAutorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testCreateAuthorSuccesfullyReturns200HttpSaved() throws Exception{
        AuthorEntity testAuthorA = TestDataUtil.createTestAutorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Rata Theodor")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(23)
        );
    }

    @Test
    public void testAuthorListReturnsHttpStatus200() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        );
        
    }

    @Test
    public void testAuthorListReturnsofAuthors() throws Exception{
        AuthorEntity testAuthorA = TestDataUtil.createTestAutorA();
        authorService.save(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].name").value("Rata Theodor")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].age").value(23)
        );
        
    }

    @Test
    public void testAuthorListReturnsHttpStatus200WhenAuthorExists() throws Exception{
        AuthorEntity testAuthorA = TestDataUtil.createTestAutorA();
        authorService.save(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/2")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        );
        
    }

    @Test
    public void testAuthorListReturnsHttpStatus4040WhenNoAuthorExists() throws Exception{
        AuthorEntity testAuthorA = TestDataUtil.createTestAutorA();
        authorService.save(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound()
        );
        
    }
    @Test
    public void testAuthorListReturnsWhenAuthorExists() throws Exception{
        AuthorEntity testAuthorA = TestDataUtil.createTestAutorA();
        authorService.save(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Rata Theodor")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(23)
        );
    }

    @Test
    public void testFullUpdateAuthorListReturnsHttpStatus404WhenNoAuthorExists() throws Exception{
        AuthorDto testAuthorDtoA = TestDataUtil.createTestAutorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound()
        ); 
    }
    @Test
    public void testFullUpdateAuthorListReturnsHttpStatus200WhenAuthorExists() throws Exception{
        AuthorEntity tesAuthorEntityA = TestDataUtil.createTestAutorA();
        AuthorEntity savedAuthor = authorService.save(tesAuthorEntityA);
        
        AuthorDto testAuthorDtoA = TestDataUtil.createTestAutorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        ); 
    }

    @Test
    public void testFullUpdatesExistingAuthor() throws Exception{
        AuthorEntity tesAuthorEntityA = TestDataUtil.createTestAutorA();
        AuthorEntity savedAuthor = authorService.save(tesAuthorEntityA);

        AuthorEntity testAuthorB = TestDataUtil.createTestAutorA();
        testAuthorB.setId(testAuthorB.getId());
        String testAuthorBJson = objectMapper.writeValueAsString(testAuthorB);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value(testAuthorB.getName())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(testAuthorB.getAge())
        );
    } 
}

    

