package com.sstankiewicz.testproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "accept.header=application/vnd.github.inertia-preview+json" })
public class RestTemplateConfugurationSetHeaderTest {

    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void checkHeader() {
        mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("/test.pl"))
                .andExpect(header("Accept", "application/vnd.github.inertia-preview+json"))
                .andRespond(withStatus(HttpStatus.OK)
                );

        restTemplate.getForObject("test.pl", String.class);
    }
}