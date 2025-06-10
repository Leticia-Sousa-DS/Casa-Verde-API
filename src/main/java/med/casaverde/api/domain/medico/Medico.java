package med.casaverde.api.domain.medico;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.casaverde.api.domain.endereco.*;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    public Medico(DadosCadastroMedico dados) {
        this.nome=dados.nome();
        this.email=dados.email();
        this.telefone=dados.telefone();
        this.crm=dados.crm();
        this.especialidade=dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
        this.ativo = true;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public void atualizarInformacoes(DadosAtualizadosMedico dados) {
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

    public void inativar() {
        this.ativo=false;
        //throw new UnsupportedOperationException("Unimplemented method 'inativar'");
    }

}
