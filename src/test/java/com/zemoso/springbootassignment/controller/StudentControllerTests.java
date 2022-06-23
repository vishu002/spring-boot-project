package com.zemoso.springbootassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoso.springbootassignment.controller.StudentController;
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
@WebMvcTest(StudentController.class)
class StudentControllerTests {

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
    void load_GetMapping_Students() throws Exception{

        List<Student> students=new ArrayList<>();
        students.add(new Student(101,"steven","grant",21,"Male","CSE","MVSR",null));

        when(studentService.findAll()).thenReturn(students);

        mockMvc
                .perform(get("/admin/student/students"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("students", hasSize(1)));

        verify(studentService, times(1)).findAll();

    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_AddStudentForm() throws Exception{
        mockMvc
                .perform(get("/admin/student/addStudentForm"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void IfValid_SaveStudent() throws Exception{

        Student student=new Student(101,"steven","grant",21,"Male","CSE","MVSR",null);

        when(studentService.findById(101)).thenReturn(Optional.of(student));
        doNothing().when(studentService).save(student);

        mockMvc.perform(post("/admin/student/saveStudent")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("rollno", String.valueOf(101))
                        .param("firstName","steven")
                        .param("lastName","grant")
                        .param("age", String.valueOf(21))
                        .param("gender","Male")
                        .param("branch","CSE")
                        .param("college","MVSR")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/student/students"));

        verify(studentService,times(1)).save(student);
        verify(studentService, times(1)).findById(101);

    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void IfInvalidDoNotSaveStudent() throws Exception{

        mockMvc.perform(post("/admin/student/saveStudent")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("rollno", String.valueOf(101))
                        .param("firstName","")
                        .param("lastName","")
                        .param("age", String.valueOf(21))
                        .param("gender","Male")
                        .param("branch","CSE")
                        .param("college","MVSR")
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_UpdateStudentForm() throws Exception{

        Student tempStudent=new Student(101,"steven","grant",21,"Male","CSE","MVSR",null);
        Optional<Student> student=Optional.of(tempStudent);

        when(studentService.findById(101)).thenReturn(student);

        mockMvc
                .perform(get("/admin/student/updateStudentForm/101"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(studentService, times(1)).findById(101);

    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_DeleteStudent() throws Exception{

        mockMvc
                .perform(get("/admin/student/deleteStudent/101"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        verify(studentService, times(1)).deleteById(101);

    }

    @Test
    @WithMockUser(username = "Arifa", password = "admin", roles = "ADMIN")
    void load_GetMapping_BorrowedBooks() throws Exception{

        List<Book> books=new ArrayList<>();
        books.add(new Book(1001,"Java","Balaguru","2021-03-09",10,null));


        Student student=new Student(101,"steven","grant",21,"Male","CSE","MVSR",books);

        List<Record> records=new ArrayList<>();
        records.add(new Record(1,101,1001,new RecordDetail(10001,"2022-05-05","2022-06-05")));


        when(studentService.findById(101)).thenReturn(Optional.of(student));
        when(recordService.findAllByStudentRollno(101)).thenReturn(records);

        mockMvc
                .perform(get("/admin/student/borrowedBooks/101"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(studentService, times(1)).findById(101);
        verify(recordService, times(1)).findAllByStudentRollno(101);

    }


}
