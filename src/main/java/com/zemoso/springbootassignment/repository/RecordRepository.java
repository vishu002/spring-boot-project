package com.zemoso.springbootassignment.repository;

import com.zemoso.springbootassignment.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface RecordRepository extends JpaRepository<Record, Integer> {

    List<Record> findAllByStudentRollno(int studentRollno);
    List<Record> findAllByBookId(int bookId);

    List<Record> findByStudentRollno(Integer studentRollno);


}
