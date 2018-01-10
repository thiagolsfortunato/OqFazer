package br.com.oqfazer.integration;

import br.com.oqfazer.authentication.request.AuthenticationRequest;
import br.com.oqfazer.authentication.response.AuthenticationResponse;
import br.com.oqfazer.configuration.AbstractApplicationTest;
import br.com.oqfazer.domain.city.City;
import br.com.oqfazer.domain.region.Region;
import br.com.oqfazer.domain.region.RegionRepository;
import br.com.oqfazer.domain.region.RegionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CityTest extends AbstractApplicationTest {

    private String token = null;

    @Autowired
    RegionService service;

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
        Region regionEntity = service.save(region);

        City city = new City("São José dos Campos", regionEntity);
        String jsonInString = mapper.writeValueAsString(city);
        int status = super.mockMvcPerformAuthenticatedPostStatus("/api/city", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isCreated(), token);
        Assert.assertEquals(201, status);

        /**
         * Test Search By Name
         */
        String result = super.mockMvcPerformGetRequestParam("/api/city", "name", "São José dos Campos");
        City cityEntity = mapper.readValue(result, City.class);
        Assert.assertNotNull(cityEntity);

        /**
         * Test Edit
         */
        city.setName("Jacareí");
        jsonInString = mapper.writeValueAsString(city);
        status = super.mockMvcPerformAuthenticatedPutResult("/api/city", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

        /**
         * Test Delete
         */
        jsonInString = mapper.writeValueAsString(city);
        status = super.mockMvcPerformAuthenticatedDeleteResult("/api/city", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

    }

    @Test
    public void getAllCitiesTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String result = super.mockMvcPerformGetAll("/api/cities");
        List<City> cityList = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, City.class));
        Assert.assertNotNull(cityList);
    }
}
