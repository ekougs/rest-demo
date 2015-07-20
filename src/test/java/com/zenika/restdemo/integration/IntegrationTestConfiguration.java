package com.zenika.restdemo.integration;

import com.zenika.restdemo.resources.HealthResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * User: sennen
 * Date: 19/05/15
 * Time: 10:32
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {HealthResource.class})
public class IntegrationTestConfiguration {

  @Bean
  public ResourceConfig jerseyConfig() {
    ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.packages(true, HealthResource.class.getPackage().getName());
    return resourceConfig;
  }
}
