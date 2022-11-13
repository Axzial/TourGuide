package fr.axzial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class Gateway {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Gateway.class, args);
        DiscoveryClient bean = run.getBean(DiscoveryClient.class);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("gps-api",
                        r -> r.path("/gps-api/**")
                                .filters(rw -> rw.rewritePath("/gps-api/(?<segment>.*)", "/${segment}"))
                                .uri("lb://GPS-API")
                )
                .route("rewards-api",
                        r -> r.path("/rewards-api/**")
                                .filters(rw -> rw.rewritePath("/rewards-api/(?<segment>.*)", "/${segment}"))
                                .uri("lb://REWARDS-API")
                )
                .build();
    }

}
