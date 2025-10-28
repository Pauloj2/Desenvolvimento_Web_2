package com.example.desenvWebll.plantio;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.desenvWebll.controller.PlantioController;
import com.example.desenvWebll.model.Plantio;
import com.example.desenvWebll.service.PlantioService;

import com.example.desenvWebll.config.TestConfig;

@WebMvcTest(PlantioController.class)
@Import(TestConfig.class)
public class PlantioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlantioService plantioService;

    @AfterEach
    void resetMocks() {
        reset(plantioService);
    }

    private List<Plantio> testCreatePlantioList() {
        Plantio plantio = new Plantio();
        plantio.setId(1L);
        plantio.setNome("Plantio de Milho");
        plantio.setDataPlantio(LocalDate.of(2025, 3, 15));
        plantio.setDataColheita(LocalDate.of(2025, 9, 20));
        plantio.setQuantidadeSementes(120.0);
        plantio.setProdutividade(85.5);

        return List.of(plantio);
    }

    @Test
    @DisplayName("GET /plantio - Acesso negado sem autenticação")
    void testIndexNotAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/plantio"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("GET /plantio - Listar plantios com usuário autenticado")
    void testIndexAuthenticatedUser() throws Exception {
        when(plantioService.getAllPlantios()).thenReturn(testCreatePlantioList());

        mockMvc.perform(get("/plantio"))
                .andExpect(status().isOk())
                .andExpect(view().name("plantio/index"))
                .andExpect(model().attributeExists("plantiosList"))
                .andExpect(content().string(containsString("Listagem de Plantio")))
                .andExpect(content().string(containsString("Plantio de Milho")));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("GET /plantio/ - Exibe link de acesso ao form de cadastro de produto")
    void testCreateFormAuthorizedUser() throws Exception {
        when(plantioService.getAllPlantios()).thenReturn(testCreatePlantioList());
        mockMvc.perform(get("/plantio"))
                .andExpect(status().isOk())
                .andExpect(view().name("plantio/index"))
                .andExpect(content().string(containsString("Cadastrar Plantio")));
    }

    @Test
    @WithMockUser(username = "aluno2@iftm.edu.br", authorities = { "Manager" })
    @DisplayName("GET /plantio/create - Usuário não admin sem permissão de criação")
    void testCreateFormNotAuthorizedUser() throws Exception {
        when(plantioService.getAllPlantios()).thenReturn(testCreatePlantioList());

        mockMvc.perform(get("/plantio"))
                .andExpect(status().isOk())
                .andExpect(view().name("plantio/index"))
                .andExpect(content().string(
                        not(containsString("<a class=\"dropdown-item\" href=\"/plantio/create\">Cadastrar</a>"))));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /plantio/save - Erro de validação ao enviar campos obrigatórios vazios")
    void testSavePlantioValidationError() throws Exception {
        Plantio plantio = new Plantio(); // Campos obrigatórios ausentes

        mockMvc.perform(post("/plantio/save")
                .with(csrf())
                .flashAttr("plantio", plantio))
                .andExpect(status().isOk())
                .andExpect(view().name("plantio/create"))
                .andExpect(model().attributeHasErrors("plantio"));

        verify(plantioService, never()).savePlantio(any(Plantio.class));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("POST /plantio/save - Plantio válido é salvo com sucesso")
    void testSaveValidPlantio() throws Exception {
        Plantio plantio = new Plantio();
        plantio.setNome("Plantio de Soja");
        plantio.setDataPlantio(LocalDate.of(2025, 1, 10));
        plantio.setDataColheita(LocalDate.of(2025, 6, 20));
        plantio.setQuantidadeSementes(200.0);
        plantio.setProdutividade(150.0);

        mockMvc.perform(post("/plantio/save")
                .with(csrf())
                .flashAttr("plantio", plantio))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/plantio"));

        verify(plantioService).savePlantio(any(Plantio.class));
    }
}
