package com.zemoso.springbootassignment.controller;

import com.zemoso.springbootassignment.controller.GlobalController;
import com.zemoso.springbootassignment.service.BookService;
import com.zemoso.springbootassignment.service.RecordService;
import com.zemoso.springbootassignment.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.zemoso.springbootassignment.LibraryManagementApplication;

@RunWith(SpringRunner.class)
@WebMvcTest(GlobalController.class)
class GlobalControllerTests {

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

    @Test
    void home() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
    }

    @Test
    void login() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/login")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void accessDenied() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/accessDenied")).andExpect(status().isOk());
    }

}
