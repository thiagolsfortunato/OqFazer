package br.com.oqfazer.integration;

import br.com.oqfazer.authentication.request.AuthenticationRequest;
import br.com.oqfazer.authentication.response.AuthenticationResponse;
import br.com.oqfazer.configuration.AbstractApplicationTest;
import br.com.oqfazer.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Create by Thiago Fortunato on 28/12/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserTest extends AbstractApplicationTest {

    private String token = null;

    @Before
    public void loadContext() {
        super.setUpContext();
    }

    @Before
    public void testLogin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user_oqfazer", "oqfazer123");
        String jsonInString = mapper.writeValueAsString(authenticationRequest);
        token = super.mockMvcPerformResult("/api/auth", jsonInString, MediaType.APPLICATION_JSON_VALUE);
        AuthenticationResponse authenticationResponse = mapper.readValue(token, AuthenticationResponse.class);
        Assert.assertNotNull(authenticationResponse);
    }

    @Test
    public void testCrud() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        /**
         * Test Insert
         */
        User user = new User("eduardo", "eduardo", "eduardo123", "ROLE_ADMIN", "TEST_INSERT");
        String jsonInString = mapper.writeValueAsString(user);
        int status = super.mockMvcPerformAuthenticatedPostStatus("/api/user", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isCreated(), token);
        Assert.assertEquals(201, status);

        /**
         * Test Search By Name
         */
        String resultName = super.mockMvcPerformGetRequestParam("/api/user", "name", "eduardo");
        List<User> userList = mapper.readValue(resultName, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        Assert.assertTrue(userList.size() > 0);

        /**
         * Test Search By Username
         */
        String resultUsername = super.mockMvcPerformGetRequestParam("/api/username", "username", "eduardo");
        user = mapper.readValue(resultUsername, User.class);
        Assert.assertNotNull(user);

        /**
         * Test Edit
         */
        user.setUsername("marcelo");
        jsonInString = mapper.writeValueAsString(user);
        status = super.mockMvcPerformAuthenticatedPutResult("/api/user", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

        /**
         * Test Delete
         */
        jsonInString = mapper.writeValueAsString(user);
        status = super.mockMvcPerformAuthenticatedDeleteResult("/api/user", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);
    }

    @Test
    public void getAllUsersTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String result = super.mockMvcPerformGetAll("/api/users");
        List<User> userList = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        Assert.assertNotNull(userList);
    }
}