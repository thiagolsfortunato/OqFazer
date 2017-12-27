package br.com.oqfazer.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * A classe PresenceChannelInterceptor e responsavel por interceptar registrar os eventos de conexao
 * no servidor
 * @author Thiago Fortunato
 * @version 1.0
 */
@Component
public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {

    private final Log logger = LogFactory.getLog(StompConnectEvent.class);

    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

        if (sha.getNativeHeader("company") != null) {
            String company = sha.getNativeHeader("company").get(0);
            logger.info("Connect event [sessionId: " + sha.getSessionId() + "; company: " + company + " ]");
        }
    }
}
