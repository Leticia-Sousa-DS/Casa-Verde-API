package med.casaverde.api.domain.endereco;

import jakarta.validation.constraints.*;

public record DadosEndereco(
    @NotBlank
    String logradouro,
    @NotBlank 
    String bairro, 
    @NotBlank
    @Pattern(regexp = "\\d{8}")
    String cep, 
    @NotBlank
    String cidade, 
    @NotBlank
    String uf, 
    String numero, 
    String complemento
    ) {



}
