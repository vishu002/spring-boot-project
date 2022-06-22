package com.zemoso.springbootassignment.service;

import com.zemoso.springbootassignment.entity.Book;
import com.zemoso.springbootassignment.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(int bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void deleteById(int bookId) {
        bookRepository.deleteById(bookId);
    }


}
