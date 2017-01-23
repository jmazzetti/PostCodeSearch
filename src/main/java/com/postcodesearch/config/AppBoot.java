package com.postcodesearch.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.postcodesearch"})
@SpringBootApplication
@ConfigurationProperties(prefix="url")
public class AppBoot /*extends WebMvcConfigurerAdapter*/ {

    private String endpoint;


    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(AppBoot.class);
        app.run(args);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}