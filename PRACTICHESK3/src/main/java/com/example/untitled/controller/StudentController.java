package com.example.untitled.controller;

import com.example.untitled.model.Student;
import com.example.untitled.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("students", studentService.getAllStudents());
        return "students-list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("student", new Student());
        return "students-form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Student student, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        studentService.saveStudent(student);
        return "redirect:/students";
    }
}