package br.com.oqfazer.authentication.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A classe EntryPointUnauthorizedHandler e responsavel por sobreescrever o tipo de retorno caso
 * a requisicao enviada a API não tenha sucesso.
 * @author Thiago Fortunato
 * @version 1.0
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    /**
     * O metodo commence e responsavel por retornar o status de UNAUTHORIZED (401) ao usuario caso
     * a requisicao nao seja autorizada.
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }
}
