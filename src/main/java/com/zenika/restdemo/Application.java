package com.zenika.restdemo;

import com.mongodb.MongoClient;
import com.zenika.restdemo.resources.HealthResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.mongodb.morphia.Morphia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
  public String mongoHost() {
    return "api-shop-mongo";
  }

  @Bean
  public String mongoDBName() {
    return "shop";
  }

  @Bean
  public ResourceConfig jerseyConfig() {
    ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.packages(true, HealthResource.class.getPackage().getName());
    return resourceConfig;
  }

  @Bean
  public MongoClient mongoClient() {
    return new MongoClient(mongoHost());
  }

  @Bean
  public Morphia morphia() {
    return new Morphia();
  }
}
