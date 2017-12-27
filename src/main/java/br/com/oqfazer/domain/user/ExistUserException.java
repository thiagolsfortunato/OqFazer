package br.com.oqfazer.domain.user;

/**
 * A classe ExistUserException e reponsavel por retornar uma excecao quando o usuario
 * ja existe no banco de dados
 * @author Thiago Fortunato
 * @version 1.0
 */
public class ExistUserException extends Exception {

    private static final long serialVersionUID = 1997753363232807009L;

    public ExistUserException() {
        super("User Already Exists!");
    }

    public ExistUserException(String message) {
        super(message);
    }
}
