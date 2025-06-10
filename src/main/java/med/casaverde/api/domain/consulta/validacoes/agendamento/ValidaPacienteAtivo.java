package med.casaverde.api.domain.consulta.validacoes.agendamento;

import jakarta.validation.ValidationException;
import med.casaverde.api.domain.ValidacaoException;
import med.casaverde.api.domain.consulta.DadosAgendamentoConsulta;
import med.casaverde.api.domain.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacienteAtivo implements ValidaAgendamentoConsultas {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if(dados.idPaciente() == null){
            return;
        }

        var isPacienteAtivo = repository.findAtivoById(dados.idPaciente());
        if(!isPacienteAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com um paciente desligado");
        }
    }
}
