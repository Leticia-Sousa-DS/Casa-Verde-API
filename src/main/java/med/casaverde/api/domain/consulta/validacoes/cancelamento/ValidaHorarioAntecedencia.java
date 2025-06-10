package med.casaverde.api.domain.consulta.validacoes.cancelamento;

import jakarta.validation.ValidationException;
import med.casaverde.api.domain.ValidacaoException;
import med.casaverde.api.domain.consulta.ConsultaRepository;
import med.casaverde.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidaHorarioAntecedenciaCancelamento")
public class ValidaHorarioAntecedencia implements ValidaCancelamentoDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados){
        var consulta = repository.getReferenceById(dados.idConsulta());
        var tempoAtual = LocalDateTime.now();
        var diferencaHoras = Duration.between(tempoAtual, consulta.getData()).toHours();

        if(diferencaHoras < 24) {
            throw new ValidacaoException("Cancelamentos de consulta podem ser realizados somente com antecedência mínima de 24 horas");
        }
    }
}
