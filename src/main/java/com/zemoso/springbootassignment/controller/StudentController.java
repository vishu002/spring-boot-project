package com.zemoso.springbootassignment.controller;

import com.zemoso.springbootassignment.entity.Book;
import com.zemoso.springbootassignment.entity.Record;
import com.zemoso.springbootassignment.entity.Student;
import com.zemoso.springbootassignment.service.BookService;
import com.zemoso.springbootassignment.service.RecordService;
import com.zemoso.springbootassignment.service.StudentService;
import com.zemoso.springbootassignment.util.BorrowedBookDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/student")
public class StudentController {

    private static final String STUDENT = "student";
    private static final String STUDENT_FORM = "student-form";

    // to remove extra spaces
    @InitBinder
    public void initBinder (WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    RecordService recordService;

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @GetMapping("/students")
    public String students(Model model){
        model.addAttribute("students", studentService.findAll());
        return "students";
    }

    @GetMapping("/addStudentForm")
    public String addStudentForm(Model model){
        Student student=new Student();
        model.addAttribute(STUDENT,student);
        return STUDENT_FORM;
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult result,Model model){

        if(result.hasErrors()){
            student.setRollno(null);
            model.addAttribute(STUDENT,student);
            return STUDENT_FORM;
        }
        else{
            List<Book> books=null;
            Optional<Student> getStudent= Optional.empty();
            if(student.getRollno()!=null)
                getStudent=studentService.findById(student.getRollno());
            if(getStudent.isPresent()){
                books=getStudent.get().getBooks();
            }
            student.setBooks(books);
            studentService.save(student);
            return "redirect:/admin/student/students";
        }
    }

    @GetMapping("/updateStudentForm/{studentRollno}")
    public String updateStudentForm(@PathVariable int studentRollno, Model model){
        Optional<Student> student= studentService.findById(studentRollno);
        student.ifPresent(value -> model.addAttribute(STUDENT, value));
        return STUDENT_FORM;
    }

    @GetMapping("/deleteStudent/{studentRollno}")
    public String deleteStudent(@PathVariable int studentRollno, Model model){
        studentService.deleteById(studentRollno);
        return "redirect:/admin/student/students";
    }

    @GetMapping("/borrowedBooks/{studentRollno}")
    public String getStudentBorrowedBooks(@PathVariable int studentRollno,Model model){
        Optional<Student> getStudent=studentService.findById(studentRollno);
        Student student=new Student();
        if(getStudent.isPresent())
            student=getStudent.get();

        List<Book> borrowedBooks=student.getBooks();
        List<Record> records=recordService.findAllByStudentRollno(studentRollno);
        int size=borrowedBooks.size();
        List<BorrowedBookDetail> borrowedBookDetails=new ArrayList<>();
        for(int index=0;index<size;index++){
            BorrowedBookDetail borrowedBookDetail=new BorrowedBookDetail();
            borrowedBookDetail.setBook(borrowedBooks.get(index));
            borrowedBookDetail.setRecord(records.get(index));
            borrowedBookDetails.add(borrowedBookDetail);
        }

        model.addAttribute("borrowedBookDetails",borrowedBookDetails);
        model.addAttribute(STUDENT,student.getFirstName()+" "+student.getLastName()+" (Id : "+studentRollno+")");

        return "borrowed-books";
    }


}
