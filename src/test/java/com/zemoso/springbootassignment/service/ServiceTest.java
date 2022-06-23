package com.zemoso.springbootassignment.service;


import com.zemoso.springbootassignment.entity.Book;
import com.zemoso.springbootassignment.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;


//    @Test
//    void findBookById() {
//        Book book = new Book(0, "yash", "yuga", "2022-06-22",10);
//
//        when(bookRepository.findById(0)).thenReturn(Optional.of(book));
//
//        assertEquals(book, Optional.of(bookService.findById(0)));
//    }
//    @Test
//    void findBookByIdException() {
//        Book book = new Book(0, "yash", "yuga", "2022-06-22",10);
//        when(bookRepository.findById(2)).thenReturn(Optional.of(book));
//
//        assertThrows(RuntimeException.class, ()-> bookService.findById(0));
//    }

    @Test
    void saveBook() {
        Book book = new Book(0, "yash", "yuga", "2022-06-22",10);

        bookService.save(book);
        verify(bookRepository).save(book);
    }

    @Test
    void deleteBookById() {
        bookService.deleteById(1);
        verify(bookRepository).deleteById(1);
    }
    @Test
    void findAllBooks() {
        when(bookRepository.findAll())
                .thenReturn(Stream.of(new Book(0, "yash", "yuga", "2022-06-22",10), new Book(0, "Rakesh", "Reddy", "2022-06-22",0)).collect(Collectors.toList()));

        assertEquals(2, bookService.findAll().size());
    }

}