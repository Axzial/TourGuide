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

    @Bean
    public WebClient webClient() {
        HttpClient client = HttpClient.create(ConnectionProvider.builder("custom")
                .maxIdleTime(Duration.ofSeconds(120))
                .build());
        return WebClient.builder()
                .baseUrl(apiUrl)
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
    }

}
