package com.zemoso.springbootassignment.service;

import com.zemoso.springbootassignment.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    void save(Book book);

    Optional<Book> findById(int bookId);

    void deleteById(int bookId);
}
