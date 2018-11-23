package com.mlesniak;

import com.mlesniak.authentication.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        SpringApplication.run(Application.class, args);

//        String clientid = System.getenv("clientid");
//        String secret = System.getenv("secret");
//
//        OAuthService service = new ServiceBuilder(clientid)
//                .apiSecret(secret)
//                .build(GitHubApi.instance());


//        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
//        server.createContext("/callback", new CallbackController(service));
//        server.setExecutor(null); // creates a default executor
//        server.start();


//        String authorizationUrl = ((OAuth20Service) service).getAuthorizationUrl();
//        System.out.println(authorizationUrl);

//        OAuth2AccessToken token = ((OAuth20Service) service).getAccessToken(code);
//        System.out.println(token.getAccessToken());
//        System.out.println(token.getExpiresIn());

//        String accessToken = "66bb87d0b34e7fab6a9ebf04ac0ac4bc12afd4b5";
//        System.out.println(accessToken);
//
//        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/user");
//        ((OAuth20Service) service).signRequest(accessToken, request);
//        Response response = service.execute(request);
//        String body = response.getBody();
//        System.out.println(body);
    }

    @Bean
//    @Scope("session")
    @SessionScope
    public User getUser() {
        return new User();
    }
}
