package br.com.oqfazer.integration;

import br.com.oqfazer.authentication.request.AuthenticationRequest;
import br.com.oqfazer.authentication.response.AuthenticationResponse;
import br.com.oqfazer.configuration.AbstractApplicationTest;
import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.category.CategoryService;
import br.com.oqfazer.domain.city.City;
import br.com.oqfazer.domain.city.CityService;
import br.com.oqfazer.domain.event.Event;
import br.com.oqfazer.domain.event.impl.EventServiceImpl;
import br.com.oqfazer.domain.region.Region;
import br.com.oqfazer.domain.region.RegionService;
import br.com.oqfazer.domain.user.User;
import br.com.oqfazer.domain.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EventTest extends AbstractApplicationTest {

    private String token = null;

    @Autowired
    EventServiceImpl eventService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RegionService regionService;

    @Autowired
    CityService cityService;

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

        Category category = categoryService.save(new Category("Show", "show category", null));
        cityService.save(new City("Sao Jose dos Campos", regionService.save(new Region("Vale do Paraiba", null))));
        Region region = regionService.findByName("Vale do Paraiba");
        User owner = userService.loadUserByUsername("user_oqfazer");

        /**
         * Test Insert
         */
        Event event = new Event("Event 1", "Desc Event 1", Calendar.getInstance(), "Local do Evento", "image.jpg", category, region, owner);
        String jsonInString = mapper.writeValueAsString(event);
        int status = super.mockMvcPerformAuthenticatedPostStatus("/api/event", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isCreated(), token);
        Assert.assertEquals(201, status);

        /**
         * Test Search By Name
         */
        String result = super.mockMvcPerformGetRequestParam("/api/event", "name", "Event 1");
        Event eventEntity = mapper.readValue(result, Event.class);
        Assert.assertNotNull(eventEntity);

        /**
         * Test Edit
         */
        eventEntity.setName("Jacareí");
        jsonInString = mapper.writeValueAsString(eventEntity);
        status = super.mockMvcPerformAuthenticatedPutResult("/api/event", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

        /**
         * Test Delete
         */
        jsonInString = mapper.writeValueAsString(eventEntity);
        status = super.mockMvcPerformAuthenticatedDeleteResult("/api/event", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

    }

    @Test
    public void getAllEventsTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String result = super.mockMvcPerformGetAll("/api/events");
        List<Event> eventList = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, Event.class));
        Assert.assertNotNull(eventList);
    }
}
