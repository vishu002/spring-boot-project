package com.zemoso.springbootassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoso.springbootassignment.controller.RecordController;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.zemoso.springbootassignment.LibraryManagementApplication;

@RunWith(SpringRunner.class)
@WebMvcTest(RecordController.class)
class RecordControllerTests {

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
    void load_GetMapping_Records() throws Exception{

        List<Record> records=new ArrayList<>();
        records.add(new Record(1,101,1001,new RecordDetail(10001,"2022-05-05","2022-06-05")));

        when(recordService.findAll()).thenReturn(records);

        mockMvc
                .perform(get("/admin/record/records"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("records", hasSize(1)));

        verify(recordService, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_AddRecordForm() throws Exception{
        List<Student> students=new ArrayList<>();
        students.add(new Student(101,"steven","grant",21,"Male","CSE","MVSR",null));

        List<Book> books=new ArrayList<>();
        books.add(new Book(1001,"Java","Balaguru","2021-03-09",10,null));

        when(studentService.findAll()).thenReturn(students);
        when(bookService.findAll()).thenReturn(books);

        mockMvc
                .perform(get("/admin/record/addRecordForm"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("students", hasSize(1)))
                .andExpect(model().attribute("books", hasSize(1)));

        verify(studentService, times(1)).findAll();
        verify(bookService, times(1)).findAll();

    }


    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void IfValid_SaveRecord() throws Exception{

        RecordDetail recordDetail=new RecordDetail(1,"2022-05-05","2022-06-05");
        Record record=new Record(1,101,1001,recordDetail);

        doNothing().when(recordService).save(record);

        mockMvc.perform(post("/admin/record/saveRecord")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("id", String.valueOf(1))
                        .param("studentRollno", String.valueOf(101))
                        .param("bookId", String.valueOf(1001))
                        .param("issuedOn","2022-05-05")
                        .param("returnOn", "2022-06-05")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/record/records"));

        verify(recordService,times(1)).save(record);

    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void IfInValid_DoNotSaveRecord() throws Exception{

        List<Student> students=new ArrayList<>();
        students.add(new Student(101,"steven","grant",21,"Male","CSE","MVSR",null));

        List<Book> books=new ArrayList<>();
        books.add(new Book(1001,"Java","Balaguru","2021-03-09",10,null));

        when(studentService.findAll()).thenReturn(students);
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(post("/admin/record/saveRecord")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("id", String.valueOf(1))
                        .param("studentRollno", String.valueOf(101))
                        .param("bookId", String.valueOf(1001))
                        .param("issuedOn","")
                        .param("returnOn", "")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(studentService, times(1)).findAll();
        verify(bookService, times(1)).findAll();

    }

}
