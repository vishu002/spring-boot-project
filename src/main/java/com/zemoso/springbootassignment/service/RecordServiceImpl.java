package com.zemoso.springbootassignment.service;

import com.zemoso.springbootassignment.entity.Record;
import com.zemoso.springbootassignment.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    private RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        super();
        this.recordRepository = recordRepository;
    }

    @Override
    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    @Override
    public void save(Record theRecord) {
        recordRepository.save(theRecord);
    }

    @Override
    public List<Record> findAllByStudentRollno(int studentRollno) {
        return recordRepository.findAllByStudentRollno(studentRollno);
    }

    @Override
    public List<Record> findAllByBookId(int bookId) {
        return recordRepository.findAllByBookId(bookId);
    }


    @Override
    public List<Record> findByStudentRollno(Integer studentRollno) {
        return recordRepository.findByStudentRollno(studentRollno);
    }


}



