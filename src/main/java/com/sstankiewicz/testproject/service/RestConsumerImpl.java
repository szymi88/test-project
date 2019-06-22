package com.sstankiewicz.testproject.service;

import com.sstankiewicz.testproject.service.exception.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;


/**
 * This class is responsible for connecting to the configured REST API and obtain users repositories.
 *
 * @author sstankiewicz
 */
@Service
public class RestConsumerImpl implements RestConsumer {

    private final String apiUrlPattern;
    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(RestConsumerImpl.class);

    @Autowired
    RestConsumerImpl(final RestTemplate restTemplate,
                     final @Value("${git.repos.api.pattern}") String apiUrlPattern) {
        this.restTemplate = restTemplate;
        this.apiUrlPattern = apiUrlPattern;
    }


    /**
     * Returns list of users repositories.
     *
     * Uses the REST API configured in application.properties as: {git.repos.api.pattern}
     *
     * @param user id
     * @return the list of user's repositories or an empty {@code Optional}
     * if the user was not found.
     * @throws ConfigurationException if configured API url returns code different than 200 or 404
     */
    public Optional<List> getUserRepositories(String user) throws ConfigurationException {
        URI url;
        try {
            url = new URL(String.format(apiUrlPattern, user)).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            logger.error("Couldn't create the API URL. Check: git.repos.api.pattern value.", e);
            throw new ConfigurationException();
        }

        try {
            return Optional.ofNullable(restTemplate.getForObject(url, List.class));
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                return Optional.empty();
            } else {
                throw new ConfigurationException();
            }
        }
    }


}
