package com.mlesniak.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class Authentication {
    @Resource
    private User user;
    private final OAuth20Service service;

    public Authentication() {
        // TODO ML application.yaml uses environment
        String clientid = System.getenv("clientid");
        String secret = System.getenv("secret");

        this.service = new ServiceBuilder(clientid)
                .apiSecret(secret)
                .build(GitHubApi.instance());
    }

    public String getAuthenticationUrl() {
        return service.getAuthorizationUrl();
    }

    public void retrieveToken(String code) {
        OAuth2AccessToken token = null;
        try {
            token = service.getAccessToken(code);
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
        user.setAccessToken(token.getAccessToken());

        enrichUser();
    }

    private void enrichUser() {
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/user");
        service.signRequest(user.getAccessToken(), request);
        try {
            Response response = service.execute(request);
            String body = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(body, Map.class);
            user.setUsername((String) map.get("login"));
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
    }

    public void revokeToken() {
        user.setAccessToken(null);
    }
}
