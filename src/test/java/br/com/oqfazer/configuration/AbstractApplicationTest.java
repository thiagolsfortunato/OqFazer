package br.com.oqfazer.configuration;

import br.com.oqfazer.OqfazerApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A classe AbstractApplicationTest disponibliza os metodos utilizados para os testes de integracao
 * utilizando MockMvc
 * @author Thiago Fortunato
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = OqfazerApplication.class)
@Transactional
public abstract class AbstractApplicationTest {

    protected static final String JSON_UTF8_MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Value("${oqfazer.token.header}")
    private String tokenHeader;

    /**
     * configuração inicial para mockar os teste de integração
     */
    protected void setUpContext() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    protected String mockMvcPerformResult(final String request, final String body, final String mediaType) throws Exception {
        return mockMvc.perform(post(request)
                .content(body)
                .contentType(mediaType)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    protected String mockMvcPerformAuthenticatedPostResult(final String request, final String body, final String mediaType, ResultMatcher status, final String authentication) throws Exception {
        return mockMvc.perform(post(request)
                .content(body)
                .header(tokenHeader, authentication)
                .contentType(mediaType)).andExpect(status).andReturn().getResponse().getContentAsString();
    }

    protected int mockMvcPerformAuthenticatedPostStatus(final String request, final String body, final String mediaType, ResultMatcher status, final String authentication) throws Exception {
        return mockMvc.perform(post(request)
                .content(body)
                .header(tokenHeader, authentication)
                .contentType(mediaType)).andExpect(status).andReturn().getResponse().getStatus();
    }

    protected int mockMvcPerformAuthenticatedPutResult(final String request, final String body, final String mediaType, ResultMatcher status, final String authentication) throws Exception {
        return mockMvc.perform(put(request)
                .content(body)
                .header(tokenHeader, authentication)
                .contentType(mediaType)).andExpect(status).andExpect(status).andReturn().getResponse().getStatus();
    }

    protected int mockMvcPerformAuthenticatedDeleteResult(final String request, final String body, final String mediaType, ResultMatcher status, final String authentication) throws Exception {
        return mockMvc.perform(delete(request)
                .content(body)
                .header(tokenHeader, authentication)
                .contentType(mediaType)).andExpect(status).andExpect(status).andReturn().getResponse().getStatus();
    }

    protected int mockMvcLoginPost(final String request, final String body, final String mediaType, ResultMatcher status) throws Exception {
        return mockMvc.perform(post(request)
                .content(body)
                .contentType(mediaType)).andExpect(status).andReturn().getResponse().getStatus();
    }

    protected String mockMvcPerformGetRequestParam(final String request, final String key, final String param) throws Exception {
        return mockMvc.perform(get(request)
                .param(key, param)
                .accept(MediaType.parseMediaType(JSON_UTF8_MEDIA_TYPE))
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    protected String mockMvcPerformGetAll(final String request) throws Exception {
        return mockMvc.perform(get(request)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
}