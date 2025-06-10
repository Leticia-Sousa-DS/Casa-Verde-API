package med.casaverde.api.domain.consulta;

import med.casaverde.api.domain.ValidacaoException;
import med.casaverde.api.domain.consulta.validacoes.agendamento.ValidaAgendamentoConsultas;
import med.casaverde.api.domain.consulta.validacoes.cancelamento.ValidaCancelamentoDeConsultas;
import med.casaverde.api.domain.medico.Medico;
import med.casaverde.api.domain.medico.MedicoRepository;
import med.casaverde.api.domain.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendarConsultasService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidaAgendamentoConsultas> validacoes;

    @Autowired
    private List<ValidaCancelamentoDeConsultas> validaCancelamento;

    public DadosDetalheConsulta agendar(DadosAgendamentoConsulta dados){

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente não existe");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico não existe");
        }

        validacoes.forEach(v -> v.validar(dados));

        var medico = escolherMedico(dados);
        if (medico == null){
            throw new ValidacaoException("Não existem médicos disponíveis na data");
        }
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data(),null);
        consultaRepository.save(consulta);
        return new DadosDetalheConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {

        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for selecionado");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())){
            throw  new ValidacaoException("Id da consulta informada não existe");
        }

        validaCancelamento.forEach(v->v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

}
