package com.test.maybank.demo.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.maybank.demo.adaptor.dto.response.GithubUserResponse;
import com.test.maybank.demo.adaptor.properties.GithubProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GithubCallServiceTest {

    private GithubCallService githubCallService;

    @Mock
    private GithubProperties githubProperties;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ObjectMapper objectMapper = new ObjectMapper();
        githubCallService = new GithubCallService(githubProperties, restTemplate, objectMapper);
    }

    @Test
    public void testGetUser() throws JsonProcessingException {
        // Mock properties
        String authHeaderValue = "Bearer <your-auth-token>";
        String apiVersion = "v3";
        when(githubProperties.getAuth()).thenReturn(authHeaderValue);
        when(githubProperties.getApiVersion()).thenReturn(apiVersion);

        // Mock parameters
        int perPage = 10;

        // Mock response
        GithubUserResponse mockResponse = new GithubUserResponse();
        // ... set properties of the mock response

        // Mock RestTemplate exchange
        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.setBearerAuth(authHeaderValue);
        expectedHeaders.setAccept(Arrays.asList(MediaType.valueOf("application/vnd.github+json")));
        expectedHeaders.add("X-GitHub-Api-Version", apiVersion);
        HttpEntity<String> expectedEntity = new HttpEntity<>(expectedHeaders);
        UriBuilder uriBuilder = UriComponentsBuilder.fromUri(URI.create("https://api.github.com/search/users"));
        uriBuilder.queryParam("per_page", perPage);
        uriBuilder.queryParam("q", "Q");
        ResponseEntity<GithubUserResponse> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.exchange(String.valueOf(uriBuilder.build()), HttpMethod.GET, expectedEntity, GithubUserResponse.class))
                .thenReturn(mockResponseEntity);

        // Call the method under test
        GithubUserResponse actualResponse = githubCallService.getUser(perPage);

        // Verify the service method was called with the correct parameters
        verify(githubProperties, times(1)).getAuth();
        verify(githubProperties, times(1)).getApiVersion();
        verify(restTemplate, times(1)).exchange(String.valueOf(uriBuilder.build()), HttpMethod.GET, expectedEntity, GithubUserResponse.class);

        // Verify the response
        assertEquals(mockResponse, actualResponse);
    }
}

