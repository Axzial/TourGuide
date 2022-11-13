package fr.axzial;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class GpsUtilApi {

    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(GpsUtilApi.class, args);
        Locale.setDefault(Locale.US);
    }

    @GetMapping
    public String test() {
        return "Hello from port " + port;
    }

}
