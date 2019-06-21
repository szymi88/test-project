package com.sstankiewicz.testproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateConfugurationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void restTemplate() {
        assertThat(restTemplate).isNotNull();
    }
}