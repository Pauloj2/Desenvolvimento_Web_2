package com.example.desenvWebll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlantioController {

    @GetMapping("/plantio")
    public String listarPlantios() {
        return "/plantio/index";
    }
    
}
