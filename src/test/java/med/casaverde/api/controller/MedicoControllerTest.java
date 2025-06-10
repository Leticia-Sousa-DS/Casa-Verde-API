package med.casaverde.api.controller;

import med.casaverde.api.domain.endereco.DadosEndereco;
import med.casaverde.api.domain.endereco.Endereco;
import med.casaverde.api.domain.medico.*;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoTester;
    private JacksonTester<DadosDetalheMedico> dadosDetalheMedicoTester;

    @MockitoBean
    private MedicoRepository repository;

    private DadosEndereco dadosEndereco(){
        return new DadosEndereco(
                "rua abcd",
                "bairro",
                "00000000",
                "Natal",
                "RN",
                null,
                null
        );
    }

    @Test
    @DisplayName("Deve retornar o código HTTP 400 quando os dados forem inválidos")
    @WithMockUser
    void cadastrar_caso1() throws Exception {
        var response = mvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar o código HTTP 200 quando os dados forem válidos")
    @WithMockUser
    void cadastrar_caso2() throws Exception {

        var dadosCadastro = new DadosCadastroMedico(
                "Medico",
                "medico@casaverde.med",
                "40028922",
                "123456",
                Especialidade.PSICOTERAPEUTA,
                dadosEndereco()
        );

        when(repository.save(any())).thenReturn(new Medico(dadosCadastro));


        var response = mvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoTester.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        var dadosDetalhados = new DadosDetalheMedico(
                null,
                dadosCadastro.nome(),
                dadosCadastro.email(),
                dadosCadastro.crm(),
                dadosCadastro.telefone(),
                dadosCadastro.especialidade(),
                new Endereco(dadosCadastro.endereco())
        );

        var jsonEsperado = dadosDetalheMedicoTester.write(dadosDetalhados).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

}