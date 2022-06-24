package com.zemoso.springbootassignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "field is required")
    @Column(name = "name")
    private String name;

    public Book(Integer id, String name, String author, String publishedOn, Integer noOfCopies) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publishedOn = publishedOn;
        this.noOfCopies = noOfCopies;
    }

    @NotEmpty(message = "field is required")
    @Column(name = "author")
    private String author;

    @Column(name = "published_on")
    @NotEmpty(message = "field is required")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}",message = "field should be in yyyy-mm-dd pattern")
    private String publishedOn;

    @Column(name = "no_of_copies")
    @NotNull(message = "field is required")
    private Integer noOfCopies;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "record",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "student_rollno")
    )
    private List<Student> students;

    public void addStudent(Student student){
        if(students==null)
            students=new ArrayList<>();
        students.add(student);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publishedOn='" + publishedOn + '\'' +
                ", noOfCopies=" + noOfCopies +
                '}';
    }

}
