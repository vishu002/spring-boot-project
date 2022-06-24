package com.zemoso.springbootassignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Entity
@Table(name = "record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Record(Integer id, Integer studentRollno, Integer bookId) {
        this.id = id;
        this.studentRollno = studentRollno;
        this.bookId = bookId;
    }

    @Column(name = "student_rollno")
    @NotNull(message = "field is required")
    private Integer studentRollno;

    @Column(name = "book_id")
    @NotNull(message = "field is required")
    private Integer bookId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_detail_id")
    private RecordDetail recordDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentRollno() {
        return studentRollno;
    }

    public void setStudentRollno(Integer studentRollno) {
        this.studentRollno = studentRollno;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public RecordDetail getRecordDetail() {
        return recordDetail;
    }

    public void setRecordDetail(RecordDetail recordDetail) {
        this.recordDetail = recordDetail;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", studentRollno=" + studentRollno +
                ", bookId=" + bookId +
                '}';
    }
}
