package gov.br.sefaz.desafio.tecnico.excecao;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
