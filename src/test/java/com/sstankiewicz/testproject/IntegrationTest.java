package com.sstankiewicz.testproject;


import org.assertj.core.util.Arrays;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "git.repos.api.pattern=https://api.test.com/users/%s/repos" })
public class IntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    private String endpointUrl;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        endpointUrl = "/projects/";
    }

    @Test
    public void testNonexistentUser() throws Exception {
        String userName = "invalid-user";

        String mockApUrl = "https://api.test.com/users/" + userName + "/repos";

        mockServer.expect(requestTo(mockApUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                );

        mockMvc.perform(get(endpointUrl + userName))
                .andExpect(status().isNotFound());

        mockServer.reset();
    }

    @Test
    public void testExistentUser() throws Exception {

        String userName = "valid_user";
        String mockApUrl = "https://api.test.com/users/" + userName + "/repos";
        JSONArray result = new JSONArray(Arrays.array("TEST", "TEST2"));

        mockServer.expect(requestTo(mockApUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(result.toString())
                );

        mockMvc.perform(get(endpointUrl + userName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("user").value(userName))
                .andExpect(jsonPath("projects").value(2))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockServer.reset();
    }
}