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
import com.theodor.databasenew.domain.dto.BookDto;
import com.theodor.databasenew.domain.entities.BookEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc) {
        this.objectMapper = new ObjectMapper();
        this.mockMvc = mockMvc;
    }

    @Test
    public void testCreateBooksSuccesfullyReturns200HttpCreated() throws Exception{

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void testCreateBooksSuccesfullyReturnsCreatedBook() throws Exception{

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );

    }

}
