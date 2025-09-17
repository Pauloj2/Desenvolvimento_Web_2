package com.example.desenvWebll.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desenvWebll.model.Plantio;
import com.example.desenvWebll.repository.PlantioRepository;
import com.example.desenvWebll.service.PlantioService;

@Service
public class PlantioServiceImpl implements PlantioService {

    @Autowired
    private PlantioRepository plantioRepository;

    @Override
    public List <Plantio> getAllPlantios(){
        return plantioRepository.findAll();
    }

    @Override
    public void savePlantio(Plantio plantio){
        this.plantioRepository.save(plantio);
    }

    @Override
    public Plantio getPlantioById(long id) {
        Optional < Plantio > optional = plantioRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @Override
    public void deletePlantioById(long id) {
        this.plantioRepository.deleteById(id);
    }
}
