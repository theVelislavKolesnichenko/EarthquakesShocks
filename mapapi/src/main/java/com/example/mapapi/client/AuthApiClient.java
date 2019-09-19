package com.example.mapapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class AuthApiClient {

    private String uri;

    @Autowired
    public AuthApiClient(Environment environment) {
        this.uri = environment.getProperty("authapi.service");;
    }

    public String getRole(String header) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", header);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        return result.getBody();
    }

    public boolean isLogged() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if(getRole(request.getHeader("Authorization")).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page missing");
        else
            return true;
    }
}
