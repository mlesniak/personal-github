package com.mlesniak;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CallbackController implements HttpHandler {
    private final OAuthService service;

    public CallbackController(OAuthService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var uri = exchange.getRequestURI();
        var query = uri.getQuery();
        var parts = query.split("=");
        var code = parts[1];
        System.out.println(parts[1]);

        OAuth2AccessToken token = null;
        try {
            token = ((OAuth20Service) service).getAccessToken(code);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String accessToken = token.getAccessToken();
        System.out.println(accessToken);
        System.out.println(token.getExpiresIn());

//        String accessToken = "66bb87d0b34e7fab6a9ebf04ac0ac4bc12afd4b5";
//        System.out.println(accessToken);
//
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/user");
        ((OAuth20Service) service).signRequest(accessToken, request);
        Response response = null;
        try {
            response = service.execute(request);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        String body = response.getBody();
        System.out.println(body);

        // TODO ML Store token on file system

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(body, Map.class);
        System.out.println(map);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }


        exchange.sendResponseHeaders(200, -1);
        exchange.getResponseBody().close();
    }
}
