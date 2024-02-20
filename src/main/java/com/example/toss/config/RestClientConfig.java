package com.example.toss.config;

import com.example.toss.service.TossHttpInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Base64;
import java.util.UUID;

@Configuration
public class RestClientConfig {
    @Value("${toss.secret}")
    private String tossSecret;


    @Bean
    public RestClient tossRestClient() {
        String basicAuth = Base64.getEncoder().encodeToString((tossSecret + ":").getBytes());
        String idempotencyKey = String.valueOf(UUID.randomUUID());

        return RestClient.builder()
                .baseUrl("https://api.tosspayments.com/v1")
                .defaultHeader("Authorization", "Basic "+basicAuth)
                .defaultHeader("Idempotency-Key", idempotencyKey)
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
