package med.casaverde.api.domain.medico;

import med.casaverde.api.domain.consulta.Consulta;
import med.casaverde.api.domain.endereco.DadosEndereco;
import med.casaverde.api.domain.pacientes.DadosCadastroPaciente;
import med.casaverde.api.domain.pacientes.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName(value = "Deve devolver null quando o único médico cadastrado não está disponível na data")
    void escolherMedicoAleatorioLivreNaDataCaso1() {
        var proximaQuartaAs14 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).atTime(14,0);
        var medico = cadastrarMedico("Medico", "medico@casaverdemed.com", "123456",Especialidade.PSICOTERAPEUTA);
        var paciente = cadastrarPaciente("Paciente0", "12345678900", "paciente@email.com");
        cadastrarConsulta(medico, paciente, proximaQuartaAs14);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.PSICOTERAPEUTA, proximaQuartaAs14);
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deve devolver medico quando ele estiver disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCaso2() {
        //given
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.PSICOTERAPEUTA);

        //when or act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.PSICOTERAPEUTA, proximaSegundaAs10);

        //then or assert
        assertThat(medicoLivre).isEqualTo(medico);
    }



    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String cpf, String email) {
        var paciente = new Paciente(dadosPaciente(nome, cpf, email));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String cpf, String email) {
        return new DadosCadastroPaciente(
                nome,
                cpf,
                email,
                "61999999999",
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }


}