package med.casaverde.api.controller;

import med.casaverde.api.domain.consulta.AgendarConsultasService;
import med.casaverde.api.domain.consulta.DadosAgendamentoConsulta;
import med.casaverde.api.domain.consulta.DadosDetalheConsulta;
import med.casaverde.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaTester;

    @Autowired
    private JacksonTester<DadosDetalheConsulta> dadosDetalheConsultaConsultaTester;

    @MockitoBean
    private AgendarConsultasService consultasService;

    @Test
    @DisplayName("Deve retornar o código HTTP 400 quando as informações forem inválidas")
    @WithMockUser
    void agendar_caso1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar o código HTTP 200 quando as informações forem válidas")
    @WithMockUser
    void agendar_caso2() throws Exception {
        var data = LocalDateTime.now().plusHours(2);
        var especialidade = Especialidade.PSICOTERAPEUTA;
        var dadosDetalhe = new DadosDetalheConsulta(null, 2L,4L, data);

        when(consultasService.agendar(any())).thenReturn(dadosDetalhe);

        var response = mvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaTester.write(
                                new DadosAgendamentoConsulta(2L, 4L,data, especialidade)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalheConsultaConsultaTester.write(
                dadosDetalhe
        ).getJson();
                assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }



}