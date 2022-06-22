package com.zemoso.springbootassignment.repository;

import com.zemoso.springbootassignment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
