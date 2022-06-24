package com.zemoso.springbootassignment.service;


import com.zemoso.springbootassignment.entity.Record;
import com.zemoso.springbootassignment.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecordServiceImplTest {
    @Autowired
    private RecordServiceImpl recordService;

    @MockBean
    private RecordRepository recordRepository;


    @Test
    void findAll() {
        when(recordRepository.findAll())
                .thenReturn(( Stream.of(new Record(1, 5,10), new Record(1, 5,10)).collect(Collectors.toList())));

        assertEquals(2, recordService.findAll().size());

}
    @Test
    void save() {
        Record record = new Record(1, 5, 10);

        recordService.save(record);
        verify(recordRepository).save(record);
    }

    @Test
    void findAllByStudent() {
        when(recordRepository.findAllByStudentRollno(1))
                .thenReturn((Stream.of(new Record(1, 5, 10), new Record(1, 5, 10)).collect(Collectors.toList())));

        assertEquals(0, recordService.findAll().size());
    }

        @Test
        void findAllByBook() {
            when(recordRepository.findAllByBookId(1))
                    .thenReturn(( Stream.of(new Record(1, 5,10), new Record(1, 5,10)).collect(Collectors.toList())));

            assertEquals(0, recordService.findAll().size());
    }
    @Test
    void findByStudentId() {
        when(recordRepository.findByStudentRollno(1))
                .thenReturn(( Stream.of(new Record(1, 5,10), new Record(1, 5,10)).collect(Collectors.toList())));

        assertEquals(0, recordService.findAll().size());
    }



}