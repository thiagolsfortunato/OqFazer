package br.com.oqfazer.integration;

import br.com.oqfazer.authentication.request.AuthenticationRequest;
import br.com.oqfazer.authentication.response.AuthenticationResponse;
import br.com.oqfazer.configuration.AbstractApplicationTest;
import br.com.oqfazer.domain.city.City;
import br.com.oqfazer.domain.region.Region;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RegionTest extends AbstractApplicationTest {

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
        Region region = new Region("Vale do Paraiba", null);
        String jsonInString = mapper.writeValueAsString(region);
        int status = super.mockMvcPerformAuthenticatedPostStatus("/api/region", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isCreated(), token);
        Assert.assertEquals(201, status);

        /**
         * Test Search By Name
         */
        String result = super.mockMvcPerformGetRequestParam("/api/region", "name", "Vale do Paraiba");
        Region regionEntity = mapper.readValue(result, Region.class);
        Assert.assertNotNull(regionEntity);

        /**
         * Test Edit
         */
        region.setName("Litoral");
        jsonInString = mapper.writeValueAsString(region);
        status = super.mockMvcPerformAuthenticatedPutResult("/api/region", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

        /**
         * Test Delete
         */
        jsonInString = mapper.writeValueAsString(region);
        status = super.mockMvcPerformAuthenticatedDeleteResult("/api/region", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

    }

    @Test
    public void getAllRegionsTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String result = super.mockMvcPerformGetAll("/api/regions");
        List<Region> regionList = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, Region.class));
        Assert.assertNotNull(regionList);
    }
}
