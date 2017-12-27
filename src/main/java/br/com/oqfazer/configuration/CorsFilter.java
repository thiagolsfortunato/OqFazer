package br.com.oqfazer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * O CORS (Cross-origin resource sharing) e uma especificacao de segurança implementada pelos browsers,
 * A classe CorsFilter e responsavel por permitir que requisicoes sejam realizadas browsers
 * @author Thiago Fortunato
 * @version 1.0
 */
@Component
public class CorsFilter implements Filter {

    @Value("${oqfazer.token.header}")
    private String tokenHeader;

    /**
     * O metodo e reponsavel por informar ao solicitante os tipos de requisicoes
     * permitidas na API e quais parametros podem ser informados no header
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " + tokenHeader);
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}
