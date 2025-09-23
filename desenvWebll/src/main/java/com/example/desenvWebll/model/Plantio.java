package com.example.desenvWebll.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "plantios")
public class Plantio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "dataPlantio", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPlantio;

    @Column(name = "dataColheita", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataColheita;

    @Column(name = "quantidadeSementes", nullable = false)
    private Double quantidadeSementes;

    @Column(name = "produtividade", nullable = false)
    private Double produtividade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataPlantio() {
        return dataPlantio;
    }

    public void setDataPlantio(LocalDate dataPlantio) {
        this.dataPlantio = dataPlantio;
    }

    public void setDataColheita(LocalDate dataColheita) {
        this.dataColheita = dataColheita;
    }

    public LocalDate getDataColheita() {
        return dataColheita;
    }

    public Double getQuantidadeSementes() {
        return quantidadeSementes;
    }

    public void setQuantidadeSementes(Double quantidadeSementes) {
        this.quantidadeSementes = quantidadeSementes;
    }

    public Double getProdutividade() {
        return produtividade;
    }

    public void setProdutividade(Double produtividade) {
        this.produtividade = produtividade;
    }

}
