package com.zenika.restdemo;

import com.zenika.restdemo.resources.HealthResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * User: sennen
 * Date: 13/05/15
 * Time: 13:53
 */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ResourceConfig jerseyConfig() {
    ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.packages(true, HealthResource.class.getPackage().getName());
    return resourceConfig;
  }
}
