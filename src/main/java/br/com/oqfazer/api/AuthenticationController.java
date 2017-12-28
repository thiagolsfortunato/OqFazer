package br.com.oqfazer.api;

import br.com.oqfazer.authentication.request.AuthenticationRequest;
import br.com.oqfazer.authentication.request.AuthenticationSocketRequest;
import br.com.oqfazer.authentication.response.AuthenticationResponse;
import br.com.oqfazer.authentication.security.TokenUtils;
import br.com.oqfazer.authentication.service.UserSocketService;
import br.com.oqfazer.authentication.user.AuthenticationUser;
import br.com.oqfazer.configuration.StompConnectEvent;
import lombok.extern.log4j.Log4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


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

    @Autowired
    private UserSocketService socketService;

    @Autowired
    private StompConnectEvent stompConnectEvent;

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
     * @param username
     * @return
     */
    @RequestMapping(path = "/auth", method = RequestMethod.GET)
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestParam("username") String username) {
        String newToken = this.tokenUtils.generateTokenByUsername(username);
        return new ResponseEntity(new AuthenticationResponse(newToken, username, "ROLE_ADMIN"), HttpStatus.OK);
    }

    /**
     * Metodo responsavel por adicionar o usuario a sessao e permitir que ele
     * faca requisicoes ao servidor
     * @param request
     * @param headerAccessor
     */
    @MessageMapping("/join")
    public void join(AuthenticationSocketRequest request, SimpMessageHeaderAccessor headerAccessor) {
        logger.info("Join " + headerAccessor.getSessionId());
        request.setRemoteAddress(headerAccessor.getSessionAttributes().get("ip").toString());
        request.setSessionId(headerAccessor.getSessionId());
        socketService.connectUser(request);
    }

    /**
     * Metodo responsavel por desconectar o usuario do servidor
     * @param request
     */
    @MessageMapping("/leave")
    public void leave(AuthenticationSocketRequest request) {
        logger.info("Leave " + request.getSessionId());
        socketService.disconnectUser(request);
    }

    /**
     * Metodo responsavel por manter a comunicao entre o front-end da aplicacao
     * para que o nginx nao durrube a sessao
     * @param request
     */
    @MessageMapping("/heartbeat")
    public void heartbeat(AuthenticationSocketRequest request) {
        logger.info("Heartbeat " + request.getToken());
        socketService.heartbeat(request);
    }

    /**
     * Evento responsavel por capturar uma conexao ao servidor
     * @param event
     */
    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        logger.info("Connected " + headers.getSessionId());
        stompConnectEvent.onApplicationEvent(event);
    }

    /**
     * Evento responsavel por capturar quando um usuario desconecta do servidor
     * @param event
     */
    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        logger.info("Disconnected " + event.getSessionId());
        socketService.disconnectUser(new AuthenticationSocketRequest(event.getSessionId()));
    }
}
