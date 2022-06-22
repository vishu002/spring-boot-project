package com.zemoso.springbootassignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @Column(name = "rollno")
    @NotNull(message = "field is required")
    private Integer rollno;

    @Column(name = "first_name")
    @NotEmpty(message = "field is required")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "field is required")
    private String lastName;

    @Column(name = "age")
    @NotNull(message = "field is required")
    private Integer age;

    @Column(name = "gender")
    @NotEmpty(message = "field is required")
    private String gender;

    @Column(name = "branch")
    @NotEmpty(message = "field is required")
    private String branch;

    @Column(name = "college")
    @NotEmpty(message = "field is required")
    private String college;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "record",
            joinColumns = @JoinColumn(name = "student_rollno"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    public void addBook(Book book){
        if(books==null)
            books=new ArrayList<>();
        books.add(book);
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollno=" + rollno +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", branch='" + branch + '\'' +
                ", college='" + college + '\'' +
                '}';
    }
}
