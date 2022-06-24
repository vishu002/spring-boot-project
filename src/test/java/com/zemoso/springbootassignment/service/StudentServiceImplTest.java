package com.zemoso.springbootassignment.service;


import com.zemoso.springbootassignment.entity.Student;
import com.zemoso.springbootassignment.errorhandler.StudentErrorResponse;
import com.zemoso.springbootassignment.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudentServiceImplTest {
    @Autowired
    private StudentServiceImpl studentService;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private StudentErrorResponse studentErrorResponse;


    @Test
    void findBookById() {
        Student student = new Student(1,"yash","yuga",22,"male","cse","pace");

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        assertEquals(Optional.of(Optional.of(student)), Optional.of(studentService.findById(1)));
    }
//    @Test
//    void findBookByIdException() {
//        StudentErrorResponse studentErrorResponse = new StudentErrorResponse(0,"error",23062022);
//        when(studentErrorResponse.getMessage()).thenReturn(String.valueOf(Optional.of(studentErrorResponse)));
//
//        assertEquals(Optional.of(RuntimeException.class), (()-> studentErrorResponse.getMessage()));
//    }

    @Test
    void saveStudent() {
        Student student = new Student(1,"yash","yuga",22,"male","cse","pace");

        studentService.save(student);
        verify(studentRepository).save(student);
    }

    @Test
    void deleteBookById() {
        studentService.deleteById(1);
        verify(studentRepository).deleteById(1);
    }
    @Test
    void findAllBooks() {
        when(studentRepository.findAll())
                .thenReturn(Stream.of(new Student(1,"yash","yuga",22,"male","cse","pace")).collect(Collectors.toList()));

        assertEquals(1, studentService.findAll().size());
    }

}