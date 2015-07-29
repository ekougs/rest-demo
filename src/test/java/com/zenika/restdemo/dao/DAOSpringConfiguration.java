package com.zenika.restdemo.dao;

import com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb.MongoServerRuleBuilder.newManagedMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;

/**
 * User: sennen
 * Date: 28/07/15
 * Time: 12:14
 */
@Configuration
@ComponentScan(basePackageClasses = CommandsRepository.class)
public class DAOSpringConfiguration {
  public static final String MONGO_DB_NAME = "test";

  @Bean
  public String mongoHost() {
    return "localhost";
  }

  @Bean
  public String mongoDBName() {
    return MONGO_DB_NAME;
  }

  @Bean
  public int mongoConnectTimeout() {
    return 3;
  }

  @Bean
  public MongoClient mongoClient() {
    return new MongoClient();
  }

  @Bean
  public Morphia morphia() {
    return new Morphia();
  }

  public static ManagedMongoDb managedMongoDb() {
    return newManagedMongoDbRule().mongodPath("/usr/local/mongodb")
                                  .appendSingleCommandLineArguments("-vvv")
                                  .build();
  }

  public static MongoDbRule mongoDbRule() {
    return newMongoDbRule().defaultManagedMongoDb(DAOSpringConfiguration.MONGO_DB_NAME);
  }
}
