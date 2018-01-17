package br.com.oqfazer.api;

import br.com.oqfazer.authentication.request.AuthenticationRequest;
import br.com.oqfazer.authentication.response.AuthenticationResponse;
import br.com.oqfazer.authentication.security.TokenUtils;
import br.com.oqfazer.authentication.user.AuthenticationUser;
import lombok.extern.log4j.Log4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


/**
 * A classe AuthenticationController é reponsavel por disponiblizar o serviço de autenticacao
 *
 * @author Thiago Fortunato
 * @version 1.0
 */
@Log4j
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final Log logger = LogFactory.getLog(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * O metodo recebe os dados de login e tenta receber suas credenciais
     * utilizando o servico de autenticacao do spring. Caso o usuario seja valido e
     * retornado uma instancia de AuthenticationResponse informando o username, tipo de autorizacao
     * e o token valido para enviar requisicoes a API.
     *
     * @param authenticationRequest
     * @return authenticationResponse
     * @throws AuthenticationException
     */
    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {

        Authentication credentials = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(credentials);

        try {
            AuthenticationUser userDetails = (AuthenticationUser) authenticationManager.authenticate(credentials).getPrincipal();
            String token = this.tokenUtils.generateTokenByUserDetails(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(token, userDetails.getUsername(), userDetails.getAuthority()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * Metodo responsavel por atualizar o token do usuario
     *
     * @param username
     * @return
     */
    @RequestMapping(path = "/auth", method = RequestMethod.GET)
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestParam("username") String username) {
        String newToken = this.tokenUtils.generateTokenByUsername(username);
        return new ResponseEntity(new AuthenticationResponse(newToken, username, "ROLE_ADMIN"), HttpStatus.OK);
    }
}
