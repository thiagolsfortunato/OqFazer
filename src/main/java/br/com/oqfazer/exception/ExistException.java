package br.com.oqfazer.exception;

/**
 * A classe ExistException e reponsavel por retornar uma excecao quando um Object
 * for encontrado no banco de dados
 * @author Thiago Fortunato
 * @version 1.0
 */
public class ExistException extends Exception {
    private static final long serialVersionUID = 1997753363232807009L;

    public ExistException() {
        super("Already Exists!");
    }

    public ExistException(String message) { super(message); }
}
