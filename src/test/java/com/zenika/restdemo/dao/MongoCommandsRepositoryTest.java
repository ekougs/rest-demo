package com.zenika.restdemo.dao;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.zenika.restdemo.model.Command;
import junit.framework.TestCase;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * User: sennen
 * Date: 28/07/15
 * Time: 12:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DAOSpringConfiguration.class)
public class MongoCommandsRepositoryTest {
  @ClassRule
  public static ManagedMongoDb managedMongoDb = DAOSpringConfiguration.managedMongoDb();

  @Rule
  public MongoDbRule remoteMongoDbRule = DAOSpringConfiguration.mongoDbRule();

  @Autowired
  private MongoCommandsRepository commandsRepository;

  @Test
  @UsingDataSet(locations = "commands.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
  public void should_return_total_number_of_commands_in_store() throws Exception {
    TestCase.assertEquals(22L, commandsRepository.count());
  }

  @Test
  @UsingDataSet(locations = "commands.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
  public void should_return_5_commands_out_of_existing_22() throws Exception {
    int from = 3;
    int size = 5;

    List<Command> commands = commandsRepository.getCommands(from, size);

    Assertions.assertThat(commands)
              .hasSize(5)
              .extracting(Command::getReference)
              .contains("FR004", "FR005", "FR006", "FR007", "FR008");
  }
}