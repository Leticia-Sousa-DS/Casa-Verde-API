package med.casaverde.api.domain.consulta.validacoes.agendamento;

import med.casaverde.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidaAgendamentoConsultas {
    void validar(DadosAgendamentoConsulta dados);
}
