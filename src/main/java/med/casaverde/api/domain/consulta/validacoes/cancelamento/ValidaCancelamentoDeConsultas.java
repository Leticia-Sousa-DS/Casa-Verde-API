package med.casaverde.api.domain.consulta.validacoes.cancelamento;

import med.casaverde.api.domain.consulta.DadosCancelamentoConsulta;

public interface ValidaCancelamentoDeConsultas {

    void validar(DadosCancelamentoConsulta dados);

}
