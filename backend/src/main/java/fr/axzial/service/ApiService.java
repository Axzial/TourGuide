package fr.axzial.service;

import lombok.Getter;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class ApiService {

    @Getter
    private final WebClient webClient;

    public ApiService(WebClient webClient) {
        this.webClient = webClient;
    }

}
