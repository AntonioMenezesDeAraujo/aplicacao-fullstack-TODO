package gov.br.sefaz.desafio.tecnico.excecao;

import gov.br.sefaz.desafio.tecnico.response.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(LoginDuplicadoException.class)
    public ResponseEntity<ErroResponse> handleLoginDuplicado(LoginDuplicadoException ex) {
        ErroResponse erro = new ErroResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(erro, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {


        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        ErroResponse erroResponse = new ErroResponse(HttpStatus.BAD_REQUEST.value(), mensagem);
        return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }
}
