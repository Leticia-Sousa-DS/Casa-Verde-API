package med.casaverde.api.domain.consulta.validacoes.agendamento;

import jakarta.validation.ValidationException;
import med.casaverde.api.domain.ValidacaoException;
import med.casaverde.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component("ValidaHorarioFuncionamentoAgendamento")
public class ValidaHorarioFuncionamento implements ValidaAgendamentoConsultas {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDeAbrir = dataConsulta.getHour() < 7;
        var depoisDeFechar = dataConsulta.getHour() > 18;
        if (domingo || antesDeAbrir || depoisDeFechar){
            throw new ValidacaoException("Agendamento fora do horário de funcionamento");
        }
    }

}
