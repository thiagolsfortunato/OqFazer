package br.com.oqfazer.integration;

import br.com.oqfazer.authentication.request.AuthenticationRequest;
import br.com.oqfazer.authentication.response.AuthenticationResponse;
import br.com.oqfazer.configuration.AbstractApplicationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Create by Thiago Fortunato on 28/12/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationTest extends AbstractApplicationTest {

    @Before
    public void loadContext() {
        super.setUpContext();
    }

    /**
     * Teste para autenticação do usuário user_oqfazer
     *
     * @throws Exception
     */
    @Test
    public void testLogin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user_oqfazer", "oqfazer123");
        String jsonInString = mapper.writeValueAsString(authenticationRequest);
        String token = super.mockMvcPerformResult("/api/auth", jsonInString, MediaType.APPLICATION_JSON_VALUE);
        AuthenticationResponse authenticationResponse = mapper.readValue(token, AuthenticationResponse.class);
        Assert.assertNotNull(authenticationResponse);
    }

    /**
     * Teste para falha na autenticação do usuário user_oqfazer com senha errada
     *
     * @throws Exception
     */
    @Test
    public void testBadLogin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user_oqfazer", "wrongPass");
        String jsonInString = mapper.writeValueAsString(authenticationRequest);
        int status = super.mockMvcLoginPost("/api/auth", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isUnauthorized());
        Assert.assertEquals(401, status);
    }
}
