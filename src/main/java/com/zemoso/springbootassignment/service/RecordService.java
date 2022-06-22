package com.zemoso.springbootassignment.service;

import com.zemoso.springbootassignment.entity.Record;

import java.util.List;

public interface RecordService {
    List<Record> findAll();

    void save(Record theRecord);

    List<Record> findAllByStudentRollno(int studentRollno);

    List<Record> findAllByBookId(int bookId);


    List<Record> findByStudentRollno(Integer studentRollno);

}
