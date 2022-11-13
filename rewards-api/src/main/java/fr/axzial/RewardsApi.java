package fr.axzial;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class RewardsApi {

    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(RewardsApi.class, args);
    }

    @GetMapping
    public String test() {
        return "Hello from port " + port;
    }

}
