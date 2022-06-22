package com.zemoso.springbootassignment.controller;

import com.zemoso.springbootassignment.entity.Record;
import com.zemoso.springbootassignment.entity.RecordDetail;
import com.zemoso.springbootassignment.service.BookService;
import com.zemoso.springbootassignment.service.RecordService;
import com.zemoso.springbootassignment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/record")
public class RecordController {

    // to remove extra spaces
    @InitBinder
    public void initBinder (WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // below two beans are for custom messages for form validation
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:application");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Autowired
    RecordService recordService;

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @GetMapping("/records")
    public String records(Model model){
        model.addAttribute("records", recordService.findAll());
        return "records";
    }

    @GetMapping("/records/{studentRollno}")
    public String getRecord(@PathVariable int studentRollno, Model model) {
        List<Record> theStudent = recordService.findByStudentRollno(studentRollno);
        model.addAttribute("records",theStudent);
        return "records";
    }


    @GetMapping("/addRecordForm")
    public String addRecordForm(Model model){
        model.addAttribute("record",new Record());
        model.addAttribute("students",studentService.findAll());
        model.addAttribute("books",bookService.findAll());
        model.addAttribute("recordDetail",new RecordDetail());
        return "record-form";
    }

    @PostMapping("/saveRecord")
    public String saveRecord(@Valid @ModelAttribute("record") Record record,BindingResult result1,@Valid @ModelAttribute("recordDetail") RecordDetail recordDetail,BindingResult result2,Model model){

        if(result1.hasErrors() || result2.hasErrors()){
            model.addAttribute("record",record);
            model.addAttribute("students",studentService.findAll());
            model.addAttribute("books",bookService.findAll());
            model.addAttribute("recordDetail",recordDetail);
            return "record-form";
        }
        else{
            record.setRecordDetail(recordDetail);
            recordService.save(record);
            return "redirect:/admin/record/records";
        }
    }

}
