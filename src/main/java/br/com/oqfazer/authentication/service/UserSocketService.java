package br.com.oqfazer.authentication.service;

import br.com.oqfazer.authentication.request.AuthenticationSocketRequest;
import br.com.oqfazer.authentication.response.AuthenticationSocketResponse;
import br.com.oqfazer.utils.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A classe UserSocketService e reponsavel por gerenciar as sessoes do servidor.
 * @author Thiago Fortunato
 * @version 1.0
 */
@Service
public class UserSocketService {

    @Autowired
    private SimpMessagingTemplate simpTemplate;

    private final Log logger = LogFactory.getLog(UserSocketService.class);

    private Map<String, AuthenticationSocketRequest> usersMap;

    /**
     * O Metodo e responsavel por criar uma sessao ao usuario
     * e enviar a mensagem para o usuario de destino
     * @param request
     */
    public void connectUser(final AuthenticationSocketRequest request) {
        String response = "joined";
        checkIfUserConnected(request);
        simpTemplate.convertAndSend(Constants.DESTINATION.getLabel() + Constants.JOIN.getLabel() + "/" + request.getToken(),
                new AuthenticationSocketResponse(request.getSessionId(), response));
        logger.info("Connected users: " + usersMap.size());
    }

    /**
     * O Metodo e responsavel por desconectar o usuario da sessao
     * e enviar uma mensagem ao usuário de destino
     * @param request
     */
    public void disconnectUser(final AuthenticationSocketRequest request) {
        if (request.getSessionId() != null) {
            Iterator<String> iterate = usersMap.keySet().iterator();

            while (iterate.hasNext()) {
                AuthenticationSocketRequest authentication = usersMap.get(iterate.next());
                if (request.getSessionId().equals(authentication.getSessionId())) {
                    iterate.remove();
                    simpTemplate.convertAndSend(Constants.DESTINATION.getLabel() + Constants.LEAVE.getLabel() + "/" + authentication.getToken(),
                            new AuthenticationSocketResponse(authentication.getSessionId(), "disconnected"));
                }
            }
        }
        logger.info("Connected users: " + usersMap.size());
    }

    /**
     * O metodo e resposnavel por manter a comunicacao entre o front e back da aplicacao
     * A cada 15 minutos o front-end envia uma requisicao para manter a conexao, pois o nginx
     * desconecta a sessao caso não haja comunicacao.
     * @param request
     */
    public void heartbeat(final AuthenticationSocketRequest request) {
        if (request.getToken() != null) {
            simpTemplate.convertAndSend(Constants.DESTINATION.getLabel() + Constants.HEARTBEAT.getLabel() + "/" + request.getToken(),
                    new AuthenticationSocketResponse(request.getSessionId(), "connected"));
        }
    }

    /**
     * O metodo verifica se um determinado usuario tem sessao no servidor
     * @param username
     * @return boolean
     */
    public boolean isConnected(final String username) {
        if (username != null) {
            for (Map.Entry<String, AuthenticationSocketRequest> entry : usersMap.entrySet()) {
                if (username.equals(entry.getKey())) return true;
            }
        }
        return false;
    }

    /**
     * O metodo verifica se um determinado usuario tem sessao no servidor
     * Caso não tenha, a requisicao e adiconada a sessao, caso contrario e
     * enviado ao usuario ja conectado uma requisicao para desconectar sua sessao
     * @param request
     * @return boolean
     */
    private boolean checkIfUserConnected(final AuthenticationSocketRequest request) {
        if (!usersMap.containsKey(request.getUsername())) {
            usersMap.put(request.getUsername(), request);
            return true;
        } else {
            try {
                AuthenticationSocketRequest user = usersMap.get(request.getUsername());
                simpTemplate.convertAndSend(Constants.DESTINATION.getLabel() + Constants.LEAVE.getLabel() + "/" + user.getToken(),
                        new AuthenticationSocketResponse(user.getSessionId(), "leave"));
                usersMap.remove(request.getUsername());
                usersMap.put(request.getUsername(), request);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Bean
    private Map<String, AuthenticationSocketRequest> getUsersMap() {
        return usersMap = new HashMap<>();
    }

}
