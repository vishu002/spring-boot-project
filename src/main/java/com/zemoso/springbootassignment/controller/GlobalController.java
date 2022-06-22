package com.zemoso.springbootassignment.controller;

import com.zemoso.springbootassignment.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("books", bookService.findAll());
        return "home";
    }

    @GetMapping("/accessDenied")
    public String showAccessDenied() {
        return "access-denied";
    }

}
