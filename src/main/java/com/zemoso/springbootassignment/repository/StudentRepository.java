package com.zemoso.springbootassignment.repository;

import com.zemoso.springbootassignment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
