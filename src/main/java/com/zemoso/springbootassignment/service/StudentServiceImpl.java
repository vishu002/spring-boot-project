package com.zemoso.springbootassignment.service;

import com.zemoso.springbootassignment.entity.Student;
import com.zemoso.springbootassignment.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(int studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public void deleteById(int studentRollno) {
        studentRepository.deleteById(studentRollno);
    }
}
