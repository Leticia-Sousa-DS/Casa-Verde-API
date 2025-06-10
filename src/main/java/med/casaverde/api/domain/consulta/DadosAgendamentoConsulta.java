package med.casaverde.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.casaverde.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        @JsonAlias("data_da_consulta")
        LocalDateTime data,

        Especialidade especialidade
) {

}
