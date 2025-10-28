package com.example.desenvWebll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.desenvWebll.model.Plantio;
import com.example.desenvWebll.service.PlantioService;
import jakarta.validation.Valid;

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
        return "plantio/form";
    }

    @PostMapping("/plantio/save")
    public String save(@ModelAttribute @Valid Plantio plantio, BindingResult result, Model model) {

        System.out.println(plantio);
        if (result.hasErrors()) {
            model.addAttribute("plantio", plantio);
            return "plantio/form";
        }

        plantioService.savePlantio(plantio);
        return "redirect:/plantio";
    }

    @GetMapping("/plantio/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.plantioService.deletePlantioById(id);
        return "redirect:/plantio";
    }

    @GetMapping("/plantio/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Plantio plantio = plantioService.getPlantioById(id);
        model.addAttribute("plantio", plantio);
        return "plantio/form";
    }
}
