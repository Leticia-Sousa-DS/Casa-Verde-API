package med.casaverde.api.domain.pacientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.casaverde.api.domain.endereco.DadosEndereco;

public record DadosAtualizadosPaciente(
    @NotNull
    Long id, 
    String nome, 
    String email,
    String telefone, 
    @Valid
    DadosEndereco endereco

) {

}
