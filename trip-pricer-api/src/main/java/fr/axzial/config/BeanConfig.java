package fr.axzial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tripPricer.TripPricer;

@Configuration
public class BeanConfig {

    @Bean
    public TripPricer tripPricer() {
        return new TripPricer();
    }

}
