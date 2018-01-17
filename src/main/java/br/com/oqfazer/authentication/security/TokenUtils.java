package br.com.oqfazer.authentication.security;

import br.com.oqfazer.authentication.user.AuthenticationUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A classe TokenUtils e responsavel por gerenciar os tokens da aplicacao
 *
 * @author Thiago Fortunato
 * @version 1.0
 */
@Component
public class TokenUtils {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Value("${oqfazer.token.secret}")
    private String secret;

    /**
     * O metodo e responsavel por obter o nome do usuario a partir do token enviado
     *
     * @param token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        String username = null;
        if (token != null) {
            try {
                final Claims claims = this.getClaimsFromToken(token);
                username = claims.getSubject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return username;
    }

    /**
     * O metodo e responsavel por retornar a data de criacao do token gerado
     *
     * @param token
     * @return date
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long) claims.get("created"));
        } catch (Exception e) {
            e.printStackTrace();
            created = null;
        }
        return created;
    }

    /**
     * O metodo e responsavel por retornar as informacoes contidas no token
     *
     * @param token
     * @return claims
     * @throws UnsupportedEncodingException
     */
    private Claims getClaimsFromToken(String token) throws UnsupportedEncodingException {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            claims = null;
        }
        return claims;
    }

    /**
     * O metodo e responsavel retornar a data atual para ser inserido no token
     *
     * @return Date
     */
    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * O metodo e responsavel por gerar o token a partir de um usuario
     *
     * @param userDetails
     * @return token
     */
    public String generateTokenByUserDetails(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("sub", userDetails.getUsername());
        claims.put("created", this.generateCurrentDate());
        return this.generateTokenByUserDetails(claims);
    }

    /**
     * O metodo e responsavel por gerar o token a partir de um usuario
     *
     * @param username
     * @return token
     */
    public String generateTokenByUsername(String username) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("sub", username);
        claims.put("created", this.generateCurrentDate());
        return this.generateTokenByUserDetails(claims);
    }

    /**
     * O metodo e reponsavel por gerar a criptografia das informacoes
     *
     * @param claims
     * @return
     */
    private String generateTokenByUserDetails(Map<String, Object> claims) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, this.secret.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            logger.warn(ex.getMessage());
            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, this.secret)
                    .compact();
        }
    }

    /**
     * O metodo e responsavel por validar se o usuario contido no token
     * e o mesmo presente no banco de dados
     *
     * @param token
     * @param userDetails
     * @return boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        if (userDetails != null) {
            AuthenticationUser user = (AuthenticationUser) userDetails;
            final String username = this.getUsernameFromToken(token);
            boolean response = (user.getUsername().equals(username));
            return response;
        }
        return false;
    }
}
