package br.com.oqfazer.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * A classe IpHandshakeInterceptor e responsavel por interceptar registrar os endereços de conexoes
 * realizadas no servidor
 * @author Thiago Fortunato
 * @version 1.0
 */
public class IpHandshakeInterceptor implements HandshakeInterceptor {

    private final Log logger = LogFactory.getLog(IpHandshakeInterceptor.class);

    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // Set ip attribute to WebSocket session
        attributes.put("ip", request.getRemoteAddress());
        logger.info("IP: " + request.getRemoteAddress());
        return true;
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
