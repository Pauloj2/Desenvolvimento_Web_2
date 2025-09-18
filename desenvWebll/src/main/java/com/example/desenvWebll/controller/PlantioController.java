package com.example.desenvWebll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.desenvWebll.model.Plantio;
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

    @GetMapping("/plantio/create")
    public String create(Model model) {
        model.addAttribute("plantio", new Plantio());
        return "plantio/create";
    }

    @PostMapping("/plantio/save")
    public String postMethodName(@ModelAttribute("plantio") Plantio plantio) {
        plantioService.savePlantio(plantio);
        return "redirect:/plantio";
    }
}
