package ru.luxtington.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lib")
public class MainController {
    @GetMapping("/index")
    public String index(){
        return "library_index";
    }
}
