package med.casaverde.api.domain.consulta.validacoes.agendamento;

import jakarta.validation.ValidationException;
import med.casaverde.api.domain.ValidacaoException;
import med.casaverde.api.domain.consulta.ConsultaRepository;
import med.casaverde.api.domain.consulta.DadosAgendamentoConsulta;
import med.casaverde.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidaHorarioAntecedencia implements ValidaAgendamentoConsultas {


    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var tempoAtual = LocalDateTime.now();
        var diferencaMinutos = Duration.between(tempoAtual, dataConsulta).toMinutes();
        if (diferencaMinutos < 30){
            throw new ValidacaoException("A consulta deve ser agendada com pelo menos 30 minutos de antecedência");
        }
    }

}
