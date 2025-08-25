package gov.br.sefaz.desafio.tecnico.excecao;

public class LoginDuplicadoException extends RuntimeException {

    public LoginDuplicadoException(String msg) {
        super(msg);
    }
}
