package br.com.oqfazer.authentication.response;

import lombok.Data;

import java.io.Serializable;

/**
 * A classe AuthenticationResponse e responsavel por encapsular os atributos autenticados do usuario
 * @author Thiago Fortunato
 * @version 1.0
 */
@Data
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = -6624726180748515507L;
    private String token;
    private String username;
    private String authority;

    public AuthenticationResponse() {
        super();
    }

    public AuthenticationResponse(String token, String username, String authority) {
        this.token = token;
        this.username = username;
        this.authority = authority;
    }
}
