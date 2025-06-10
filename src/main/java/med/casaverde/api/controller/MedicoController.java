package med.casaverde.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.casaverde.api.domain.medico.DadosAtualizadosMedico;
import med.casaverde.api.domain.medico.DadosCadastroMedico;
import med.casaverde.api.domain.medico.DadosDetalheMedico;
import med.casaverde.api.domain.medico.DadosListagemMedico;
import med.casaverde.api.domain.medico.Medico;
import med.casaverde.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        var medico = new Medico(dados);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalheMedico(medico));
        
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort={"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizadosMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);   

        return ResponseEntity.ok(new DadosDetalheMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity inativar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.inativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheMedico(medico));
    }

}
