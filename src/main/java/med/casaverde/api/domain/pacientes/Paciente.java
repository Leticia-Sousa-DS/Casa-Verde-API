package med.casaverde.api.domain.pacientes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.casaverde.api.domain.endereco.Endereco;

@Getter
@EqualsAndHashCode(of="id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Paciente")
@Table(name ="pacientes")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente(DadosCadastroPaciente dados){
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.ativo=true;
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizadosPaciente dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.email()!= null){
            this.email = dados.email();
        }
        if(dados.telefone()!=null){
            this.telefone = dados.telefone();
        }
        if(dados.endereco()!= null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
        //throw new UnsupportedOperationException("Unimplemented method 'atualizarInformacoes'");
    }

    public void inativar(){
        this.ativo = false;
    }

}
