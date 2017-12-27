package br.com.oqfazer.authentication.request;

import lombok.Data;

import java.io.Serializable;

/**
 * A classe AuthenticationSocketRequest e responsavel por encapsular os atributos de autenticacao via socket do usuario
 * @author Thiago Fortunato
 * @version 1.0
 */
@Data
public class AuthenticationSocketRequest implements Serializable {

    private static final long serialVersionUID = 6624726180748515507L;
    private String token;
    private String username;
    private String sessionId;
    private String remoteAddress;

    public AuthenticationSocketRequest() {}

    public AuthenticationSocketRequest(String sessionId) {
        this.sessionId = sessionId;
    }

    public AuthenticationSocketRequest(String token, String username, String sessionId, String remoteAddress) {
        this.token = token;
        this.username = username;
        this.sessionId = sessionId;
        this.remoteAddress = remoteAddress;
    }

    public AuthenticationSocketRequest(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
