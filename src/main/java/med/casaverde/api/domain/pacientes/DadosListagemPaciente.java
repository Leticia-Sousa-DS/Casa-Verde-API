package med.casaverde.api.domain.pacientes;

public record DadosListagemPaciente(String nome, String email, String telefone) {
    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }

}
