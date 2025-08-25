package gov.br.sefaz.desafio.tecnico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.br.sefaz.desafio.tecnico.entidades.Status;
import gov.br.sefaz.desafio.tecnico.request.TarefaRequest;
import gov.br.sefaz.desafio.tecnico.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TarefaService tarefaService;

    private Long tarefaIdCriada = 1L;

    @BeforeEach
    void setup() {
        // Cria uma tarefa antes de cada teste
//        TarefaRequest request = new TarefaRequest();
//        request.setTitulo("Tarefa inicial");
//        request.setDescricao("Descrição inicial");
//
//        TarefaResponse response = tarefaService.salvarTarefa(request);
//        tarefaIdCriada = response.getId();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deveListarTarefas() throws Exception {
        mockMvc.perform(get("/tarefa")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tarefas", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.tarefas[0].titulo", is("Tarefa Inicial")));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deveAtualizarTarefa() throws Exception {
        TarefaRequest request = new TarefaRequest();
        request.setId(tarefaIdCriada);
        request.setTitulo("Tarefa Atualizada");
        request.setDescricao("Descrição Atualizada");
//        request.setIdUsuario(1L);
        request.setStatus(Status.CONCLUIDA);

        mockMvc.perform(put("/tarefa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Tarefa Atualizada")))
                .andExpect(jsonPath("$.descricao", is("Descrição Atualizada")));
    }

//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void deveDeletarTarefa() throws Exception {
//        mockMvc.perform(delete("/tarefa/{id}", tarefaIdCriada))
//                .andExpect(status().isNoContent());
//
//        mockMvc.perform(get("/tarefa"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.tarefas", hasSize(0)));
//    }
}
