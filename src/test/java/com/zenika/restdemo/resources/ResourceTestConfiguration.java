package com.zenika.restdemo.resources;

import com.zenika.restdemo.dao.CommandsRepository;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;

import static org.mockito.Mockito.mock;

/**
 * User: sennen
 * Date: 19/05/15
 * Time: 10:32
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {HealthResource.class})
public class ResourceTestConfiguration {

  @Bean
  public ResourceConfig jerseyConfig() {
    ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.packages(true, HealthResource.class.getPackage().getName());
    return resourceConfig;
  }

  @Bean
  public CommandsRepository commandsRepository() {
    return mock(CommandsRepository.class);
  }

  @Bean
  public MongoDbFactory mongoDbFactory() throws Exception {
    return mock(MongoDbFactory.class);
  }
}
