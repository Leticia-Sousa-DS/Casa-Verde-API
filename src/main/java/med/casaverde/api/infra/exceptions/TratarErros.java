package med.casaverde.api.infra.exceptions;

import med.casaverde.api.domain.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratarErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception){
        var erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroDeValidacao::new).toList());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    private record DadosErroDeValidacao(String campo, String mensagem){
        public DadosErroDeValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
