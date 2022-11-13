package fr.axzial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

@Configuration
public class BeanConfig {

    @Bean
    public RewardCentral gpsUtil() {
        return new RewardCentral();
    }

}
