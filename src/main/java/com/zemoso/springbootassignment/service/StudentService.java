package com.zemoso.springbootassignment.service;

import com.zemoso.springbootassignment.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    void save(Student student);

    Optional<Student> findById(int studentId);

    void deleteById(int studentRollno);
}
