package br.com.oqfazer.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * A classe PresenceChannelInterceptor e responsavel por interceptar registrar os eventos de conexao
 * no servidor
 * @author Thiago Fortunato
 * @version 1.0
 */
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

    private final Log logger = LogFactory.getLog(PresenceChannelInterceptor.class);

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        // ignore non-STOMP messages like heartbeat messages
        if (sha.getCommand() == null) {
            return;
        }

        String sessionId = sha.getSessionId();

        switch (sha.getCommand()) {
            case CONNECT:
                logger.info("STOMP Connect [sessionId: " + sessionId + "]");
                break;
            case CONNECTED:
                logger.info("STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case DISCONNECT:
                logger.info("STOMP Disconnect [sessionId: " + sessionId + "]");
                break;
            default:
                break;

        }
    }
}
