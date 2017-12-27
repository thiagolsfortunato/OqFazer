package br.com.oqfazer.exception;

/**
 * A classe NotFoundException e reponsavel por retornar uma excecao quando um Object
 * nao foi encontrado no banco de dados
 * @author Thiago Fortunato
 * @version 1.0
 */
public class NotFoundException extends Exception {
    private static final long serialVersionUID = 1997753363232807009L;

    public NotFoundException() {
        super("Not Found!");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
