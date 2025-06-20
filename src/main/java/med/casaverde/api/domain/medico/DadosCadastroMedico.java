package med.casaverde.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.casaverde.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(

    @NotBlank(message = "Nome é obrigatório")
    String nome, 
    @NotBlank (message = "Email é obrigatório")
    @Email (message = "Formato do email é inválido")
    String email, 
    @NotBlank (message = "Telefone é obrigatório")
    String telefone,
    @NotBlank (message = "CRM é obrigatório")
    @Pattern(regexp = "\\d{4,6}", message = "Formato de CRM é inválido")
    String crm, 
    @NotNull (message = "Especialidade é obrigatória")
    Especialidade especialidade, 
    @NotNull (message = "Dados de Endereço são obrigratórios")
    @Valid
    DadosEndereco endereco
    ) {

}

//OBS.: Arrume o CRM para CRP, que é usado por psicólogos e variados