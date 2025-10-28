package com.example.desenvWebll.plantio;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.desenvWebll.model.Plantio;
import com.example.desenvWebll.repository.PlantioRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa application-test.properties
@Transactional // Limpa o banco após cada teste
public class PlantioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlantioRepository plantioRepository;

    @Test
    @WithMockUser(authorities = { "Admin" })
    void testSavePlantioIntegration() throws Exception {

        Plantio plantio = new Plantio();
        plantio.setNome("Plantio de Milho");
        plantio.setDataPlantio(LocalDate.of(2025, 3, 15));
        plantio.setDataColheita(LocalDate.of(2025, 9, 20));
        plantio.setQuantidadeSementes(120.0);
        plantio.setProdutividade(85.5);

        mockMvc.perform(post("/plantio/save")
                .with(csrf())
                .flashAttr("plantio", plantio))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/plantio"));

        assertTrue(plantioRepository.findAll()
                .stream()
                .anyMatch(p -> "Plantio de Milho".equals(p.getNome())));
    }
}
