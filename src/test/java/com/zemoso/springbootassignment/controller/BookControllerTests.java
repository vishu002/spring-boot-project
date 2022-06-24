package com.zemoso.springbootassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoso.springbootassignment.controller.BookController;
import com.zemoso.springbootassignment.entity.Book;
import com.zemoso.springbootassignment.entity.Record;
import com.zemoso.springbootassignment.entity.RecordDetail;
import com.zemoso.springbootassignment.entity.Student;
import com.zemoso.springbootassignment.service.BookService;
import com.zemoso.springbootassignment.service.RecordService;
import com.zemoso.springbootassignment.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.zemoso.springbootassignment.LibraryManagementApplication;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RecordService recordService;

    @MockBean
    StudentService studentService;

    @MockBean
    BookService bookService;

    @MockBean
    DataSource dataSource;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_Books() throws Exception{
        List<Book> books=new ArrayList<>();
        books.add(new Book(1001,"Java","Balaguru","2021-03-09",10,null));

        when(bookService.findAll()).thenReturn(books);

        mockMvc
                .perform(get("/admin/book/books"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("books", hasSize(1)));

        verify(bookService, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_AddBookForm() throws Exception{
        mockMvc
                .perform(get("/admin/book/addBookForm"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void IfValid_SaveBook() throws Exception{

        Book book=new Book(1001,"Java","Balaguru","2021-03-09",10,null);

        when(bookService.findById(1001)).thenReturn(Optional.of(book));

        mockMvc.perform(post("/admin/book/saveBook")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("id", String.valueOf(1001))
                        .param("name","Java")
                        .param("author","Balaguru")
                        .param("publishedOn","2021-03-09")
                        .param("noOfCopies", String.valueOf(10))
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/book/books"));

        verify(bookService, times(1)).findById(1001);

    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void IfInValid_DoNotSaveBook() throws Exception{

        mockMvc.perform(post("/admin/book/saveBook")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("id", String.valueOf(1001))
                        .param("name","")
                        .param("author","")
                        .param("publishedOn","2021-03-09")
                        .param("noOfCopies", String.valueOf(10))
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_DeleteBook() throws Exception{

        mockMvc
                .perform(get("/admin/book/deleteBook/1001"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        verify(bookService, times(1)).deleteById(1001);

    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_StudentsBorrowed() throws Exception{

        List<Student> students=new ArrayList<>();
        students.add(new Student(101,"steven","grant",21,"Male","CSE","MVSR",null));

        Book book=new Book(1001,"Java","Balaguru","2021-03-09",10,students);

        when(bookService.findById(1001)).thenReturn(Optional.of(book));

        mockMvc
                .perform(get("/admin/book/studentsBorrowed/1001"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bookService, times(1)).findById(1001);

    }





    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_UpdateBookForm() throws Exception{

        Book tempBook=new Book(1001,"Java","Balaguru","2021-03-09",10,null);
        Optional<Book> book=Optional.of(tempBook);

        when(bookService.findById(1001)).thenReturn(book);

        mockMvc
                .perform(get("/admin/book/updateBookForm/1001"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bookService, times(1)).findById(1001);


    }

}
