package br.com.oqfazer.authentication.request;

import lombok.Data;

import java.io.Serializable;

/**
 * A classe AuthenticationRequest e responsavel por encapsular os atributos de autenticacao do usuario
 * @author Thiago Fortunato
 * @version 1.0
 */
@Data
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 6624726180748515507L;
    private String username;
    private String password;

    public AuthenticationRequest() {
        super();
    }

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

}
