package fr.axzial.config;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public GpsUtil gpsUtil() {
        return new GpsUtil();
    }

}
