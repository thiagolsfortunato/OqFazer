package br.com.oqfazer.integration;

import br.com.oqfazer.authentication.request.AuthenticationRequest;
import br.com.oqfazer.authentication.response.AuthenticationResponse;
import br.com.oqfazer.configuration.AbstractApplicationTest;
import br.com.oqfazer.domain.category.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CategoryTest extends AbstractApplicationTest {

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
        Category category = new Category("Show", "category parent", null);
        String jsonInString = mapper.writeValueAsString(category);
        int status = super.mockMvcPerformAuthenticatedPostStatus("/api/category", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isCreated(), token);
        Assert.assertEquals(201, status);

        /**
         * Test Insert with parent
         */
        Category son = new Category("Rock", "child show", category);
        String jsonInString1 = mapper.writeValueAsString(son);
        int status1 = super.mockMvcPerformAuthenticatedPostStatus("/api/category", jsonInString1, MediaType.APPLICATION_JSON_VALUE, status().isCreated(), token);
        Assert.assertEquals(201, status1);

        /**
         * Test Search By Name
         */
        String result = super.mockMvcPerformGetRequestParam("/api/category", "name", "Show");
        Category categoryEntity = mapper.readValue(result, Category.class);
        Assert.assertNotNull(categoryEntity);

        /**
         * Test Edit
         */
        category.setName("Party");
        jsonInString = mapper.writeValueAsString(category);
        status = super.mockMvcPerformAuthenticatedPutResult("/api/category", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

        /**
         * Test Delete
         */
        jsonInString = mapper.writeValueAsString(category);
        status = super.mockMvcPerformAuthenticatedDeleteResult("/api/category", jsonInString, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

        /**
         * Test Delete with Parent
         */
        jsonInString1 = mapper.writeValueAsString(son);
        status = super.mockMvcPerformAuthenticatedDeleteResult("/api/category", jsonInString1, MediaType.APPLICATION_JSON_VALUE, status().isOk(), token);
        Assert.assertEquals(200, status);

    }

    @Test
    public void getAllCategoriesTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String result = super.mockMvcPerformGetAll("/api/categories");
        List<Category> categoryList = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, Category.class));
        Assert.assertNotNull(categoryList);
    }
}
