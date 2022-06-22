package com.zemoso.springbootassignment.controller;

import com.zemoso.springbootassignment.entity.Book;
import com.zemoso.springbootassignment.entity.Student;
import com.zemoso.springbootassignment.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/book")
public class BookController {
    private static final String BOOK_FORM = "book-form";

    // to remove extra spaces
    @InitBinder
    public void initBinder (WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public String books(Model model){
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/addBookForm")
    public String addRecordForm(Model model){
        model.addAttribute("book",new Book());
        return BOOK_FORM;
    }

    @PostMapping("/saveBook")
    public String saveRecord(@Valid @ModelAttribute("book") Book book, BindingResult result,Model model){

        if(result.hasErrors()){
            model.addAttribute("book",book);
            return BOOK_FORM;
        }
        else{
            List<Student> students=null;
            Optional<Book> getBook= Optional.empty();
            if(book.getId()!=null)
                getBook=bookService.findById(book.getId());
            if(getBook.isPresent())
                students=getBook.get().getStudents();
            book.setStudents(students);
            bookService.save(book);
            return "redirect:/admin/book/books";
        }
    }

    @GetMapping("/updateBookForm/{bookId}")
    public String updateBookForm(@PathVariable int bookId,Model model){
        Optional<Book> book=bookService.findById(bookId);
        if (book.isPresent())
            model.addAttribute("book",book.get());
        return BOOK_FORM;
    }

    @GetMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable int bookId){
        bookService.deleteById(bookId);
        return "redirect:/admin/book/books";
    }


    @GetMapping("/studentsBorrowed/{bookId}")
    public String getStudentsBorrowed(@PathVariable int bookId,Model model){
        Optional<Book> getBook=bookService.findById(bookId);
        Book book=new Book();
        if(getBook.isPresent())
            book=getBook.get();
        List<Student> studentsBorrowed=book.getStudents();
        model.addAttribute("bookName",book.getName());
        model.addAttribute("studentsBorrowed",studentsBorrowed);
        return "students-borrowed";
    }

}
