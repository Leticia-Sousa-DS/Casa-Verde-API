package med.casaverde.api.domain.consulta.validacoes.agendamento;

import jakarta.validation.ValidationException;
import med.casaverde.api.domain.ValidacaoException;
import med.casaverde.api.domain.consulta.DadosAgendamentoConsulta;
import med.casaverde.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaMedicoAtivo implements ValidaAgendamentoConsultas {

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if(dados.idMedico() == null){
            return;
        }

        var isMedicoAtivo = repository.findAtivoById(dados.idMedico());
        if(!isMedicoAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com um médico desligado");
        }
    }
}
