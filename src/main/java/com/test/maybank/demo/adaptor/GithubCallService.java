package com.test.maybank.demo.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.maybank.demo.adaptor.dto.response.GithubUserResponse;
import com.test.maybank.demo.adaptor.properties.GithubProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class GithubCallService {
    public static final String X_GIT_HUB_API_VERSION = "X-GitHub-Api-Version";
    private final GithubProperties githubProperties;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GithubUserResponse getUser(Integer perPage) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(githubProperties.getAuth());
        headers.setAccept(Arrays.asList(MediaType.valueOf("application/vnd.github+json")));
        headers.add(X_GIT_HUB_API_VERSION, githubProperties.getApiVersion());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriBuilder uriBuilder= UriComponentsBuilder.fromUri(URI.create("https://api.github.com/search/users"));
        uriBuilder.queryParam("per_page",perPage);
        uriBuilder.queryParam("q","Q");
        ResponseEntity<GithubUserResponse> response = restTemplate.exchange(String.valueOf(uriBuilder.build()), HttpMethod.GET, entity, GithubUserResponse.class);

        return  response.getBody();
    }
}
