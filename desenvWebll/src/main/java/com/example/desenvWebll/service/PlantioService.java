package com.example.desenvWebll.service;

import java.util.List;

import com.example.desenvWebll.model.Plantio;

public interface PlantioService {

    List <Plantio> getAllPlantios();
    void savePlantio(Plantio plantio);
    Plantio getPlantioById(long id);
    void deletePlantioById(long id);
}