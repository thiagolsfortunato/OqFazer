package br.com.oqfazer.domain.user;

/**
 * A classe ExistUserException e reponsavel por retornar uma excecao quando o usuario
 * nao foi encontrado no banco de dados
 * @author Thiago Fortunato
 * @version 1.0
 */
public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 1997753363232807009L;

    public UserNotFoundException() {
        super("User Not Found!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
