package med.casaverde.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.casaverde.api.domain.endereco.DadosEndereco;

public record DadosAtualizadosMedico(

    @NotNull
    Long id, 
    String nome, 
    String email,
    String telefone,
    @Valid 
    DadosEndereco endereco
    ) {

    
}
