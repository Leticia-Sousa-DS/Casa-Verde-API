package med.casaverde.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.casaverde.api.domain.consulta.AgendarConsultasService;
import med.casaverde.api.domain.consulta.DadosAgendamentoConsulta;
import med.casaverde.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendarConsultasService agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dtoConsulta = agenda.agendar(dados);
        return ResponseEntity.ok(dtoConsulta);
    }

    @DeleteMapping
    @Transactional
    public  ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
        agenda.cancelar(dados);
        return  ResponseEntity.noContent().build();
    }
}
