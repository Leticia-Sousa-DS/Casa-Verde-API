package med.casaverde.api.domain.medico;

import med.casaverde.api.domain.endereco.Endereco;

public record DadosDetalheMedico(
    Long id, 
    String nome, 
    String email, 
    String crm, 
    String telefone, 
    Especialidade especialidade, 
    Endereco endereco
    ) {

        public DadosDetalheMedico (Medico medico) {
            this(
                medico.getId(), 
                medico.getNome(), 
                medico.getEmail(), 
                medico.getCrm(), 
                medico.getTelefone(), 
                medico.getEspecialidade(), 
                medico.getEndereco());
        }

}


