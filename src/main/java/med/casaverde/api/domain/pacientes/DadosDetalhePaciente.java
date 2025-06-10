package med.casaverde.api.domain.pacientes;

import med.casaverde.api.domain.endereco.Endereco;

public record DadosDetalhePaciente(
    Long id,
    String nome,
    String cpf,
    String email, 
    String telefone,
    Endereco endereco) {

        public DadosDetalhePaciente(Paciente paciente){
            this(
            paciente.getId(),
            paciente.getNome(),
            paciente.getCpf(),
            paciente.getEmail(),
            paciente.getTelefone(),
            paciente.getEndereco()
        );
        
        }

}
