package com.sstankiewicz.testproject.controller;

import com.sstankiewicz.testproject.service.ProjectsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectsController.class)
public class ProjectsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectsService projectsServiceMock;

    private String endpointUrl;

    @Before
    public void setUp() {
        endpointUrl = "/projects/";

        Mockito.when(projectsServiceMock.getProjectsCount("valid_user")).thenReturn(Optional.of(1));
        Mockito.when(projectsServiceMock.getProjectsCount("invalid_user")).thenReturn(Optional.empty());
    }

    @Test
    public void testNonexistentUser() throws Exception {
        String userName = "invalid_user";

        mockMvc.perform(MockMvcRequestBuilders.get(endpointUrl + userName))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testExistentUser() throws Exception {
        String userName = "valid_user";

        mockMvc.perform(MockMvcRequestBuilders.get(endpointUrl + userName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("user").value(userName))
                .andExpect(jsonPath("projects").value(1))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testEmptyUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(endpointUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUnsupportedMethods() throws Exception {
        String userName = "valid_user";

        mockMvc.perform(MockMvcRequestBuilders.post(endpointUrl + userName))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(MockMvcRequestBuilders.put(endpointUrl + userName))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(MockMvcRequestBuilders.delete(endpointUrl + userName))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void testServiceError() throws Exception {
        Mockito.when(projectsServiceMock.getProjectsCount("cause-exception")).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get(endpointUrl + "cause-exception"))
                .andExpect(status().is5xxServerError());

    }
}