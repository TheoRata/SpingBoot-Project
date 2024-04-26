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
import com.theodor.databasenew.services.BookService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.objectMapper = new ObjectMapper();
        this.mockMvc = mockMvc;
        this.bookService = bookService;
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus200Ok() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(
                testBookEntityA.getIsbn(), testBookEntityA
        );

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setIsbn(savedBookEntity.getIsbn());
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
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

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(
                testBookEntityA.getIsbn(), testBookEntityA
        );

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setIsbn(savedBookEntity.getIsbn());
        testBookA.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBookEntity.getIsbn() )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("1234-2345-234-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }

    @Test
    public void testListBooksSuccesfullyReturns200() throws Exception{

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
            
        );
    }

    @Test
    public void testListBooksSuccesfullyReturnsBook() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].isbn").value("1234-2345-234-0")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].title").value("Bucuresti")
        );
    }

    @Test
    public void testListBooksSuccesfullyReturns200WhenBookExists() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/1234-2345-234-0")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
            
        );
    }
    @Test
    public void testListBooksSuccesfullyReturns404WhenNoBookExists() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/1234-2345-234-99")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isNotFound()
            
        );
    }

    /**
     * Tests that the list books endpoint returns a book that exists.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testListBooksSuccesfullyReturnsBookExists() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/1234-2345-234-0")
                    .contentType(MediaType.APPLICATION_JSON)
                    
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value("1234-2345-234-0")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("Bucuresti")
        );
    }

    @Test
    public void testBookDeleteSuccesfullyReturns204() throws Exception{

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/books/1234-2345-234-99")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isNoContent()
            
        );
    }
    @Test
    public void testBookDeleteSuccesfully() throws Exception{
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/books/" + testBookEntityA.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isNoContent()
            
        );
    }






}
