package com.example.desenvWebll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.desenvWebll.service.PlantioService;

@Controller
public class PlantioController {

    @Autowired
    private PlantioService plantioService;

    @GetMapping("/plantio")
    public String index(Model model) {
        model.addAttribute("plantiosList", plantioService.getAllPlantios());
        return "plantio/index";
    }
}
