package fr.axzial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class BeanConfig {

    @Value(value = "${api.url}")
    private String apiUrl;
    private static final int MAX_CONNECTIONS = 5000;
    private static final Duration MAX_IDLE_TIME = Duration.ofSeconds(120);;

    @Bean
    public WebClient webClient() {
        HttpClient client = HttpClient.create(ConnectionProvider.builder("custom")
                .maxIdleTime(MAX_IDLE_TIME)
                .maxConnections(MAX_CONNECTIONS)
                .build());
        return WebClient.builder()
                .baseUrl(apiUrl)
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
    }

}
