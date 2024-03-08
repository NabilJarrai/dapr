package com.dapr.dapr;


import io.diagrid.dapr.profiles.DaprBasicProfile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

@SpringBootApplication
public class CustomerCatalogTestApp {

    public static void main(String[] args) {
        SpringApplication.from(CustomerCatalogTestApp::main)
                .with(TestConfigurations.class)
                .run(args);
    }

    @ImportTestcontainers(DaprBasicProfile.class)
    static class TestConfigurations {

    }
}
