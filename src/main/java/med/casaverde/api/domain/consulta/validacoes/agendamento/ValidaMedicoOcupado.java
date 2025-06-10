package med.casaverde.api.domain.consulta.validacoes.agendamento;

import jakarta.validation.ValidationException;
import med.casaverde.api.domain.ValidacaoException;
import med.casaverde.api.domain.consulta.ConsultaRepository;
import med.casaverde.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaMedicoOcupado implements ValidaAgendamentoConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoOcupadoNoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if(medicoOcupadoNoHorario){
            throw new ValidacaoException("Medico já possui outra consulta agendada nesse horário");
        }
    }
}
