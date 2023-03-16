package com.yong.clouduser.config;

import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancerConfig {
    @Bean
    public RandomRule randomRule(){
        return new RandomRule();
    }
}
