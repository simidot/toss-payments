package com.example.toss.config;

import com.example.toss.service.TossHttpInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Base64;

@Configuration
public class RestClientConfig {
    @Value("${toss.secret}")
    private String tossSecret;


    @Bean
    public RestClient tossRestClient() {
        String basicAuth = Base64.getEncoder().encodeToString((tossSecret + ":").getBytes());

        return RestClient.builder()
                .baseUrl("https://api.tosspayments.com/v1")
                .defaultHeader("Authorization", "Basic "+basicAuth)
//                .requestInitializer(request -> request.getHeaders().add("Authorization", basicAuth))
                .build();
    }

    @Bean
    public TossHttpInterface httpService() {
        return HttpServiceProxyFactory.builderFor(
                RestClientAdapter.create(tossRestClient())
        ).build().createClient(TossHttpInterface.class);
    }

}
