package com.example.desenvWebll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.desenvWebll.model.Plantio;

@Repository
public interface PlantioRepository extends JpaRepository<Plantio, Long>{
    
} 
