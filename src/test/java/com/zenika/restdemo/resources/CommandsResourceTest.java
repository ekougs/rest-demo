package com.zenika.restdemo.resources;

import com.zenika.restdemo.dao.CommandsRepository;
import com.zenika.restdemo.model.Command;
import com.zenika.restdemo.test.ResourcesAssertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.zenika.restdemo.test.ResourcesUtils.getLocalURL;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

/**
 * User: sennen
 * Date: 27/07/15
 * Time: 16:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResourceTestConfiguration.class)
@WebIntegrationTest("server.port=0")
public class CommandsResourceTest {
  private static final List<Command> COMMANDS = new ArrayList<>();

  private RestTemplate template = new TestRestTemplate();
  @Value("${local.server.port}")
  private int port;
  @Autowired
  private CommandsRepository mockCommandRepo;

  @BeforeClass
  public static void initCommands() {
    COMMANDS.add(new Command("FR001"));
    COMMANDS.add(new Command("FR002"));
    COMMANDS.add(new Command("FR003"));
    COMMANDS.add(new Command("FR004"));
    COMMANDS.add(new Command("FR005"));
    COMMANDS.add(new Command("FR006"));
    COMMANDS.add(new Command("FR007"));
  }

  @Test
  public void get_commands_should_return_all_commands() throws Exception {
    when(mockCommandRepo.getCommands(anyInt(), anyInt())).thenReturn(COMMANDS);
    when(mockCommandRepo.count()).thenReturn((long) COMMANDS.size());

    ResponseEntity<String> getCommandsResponse = template.getForEntity(getLocalURL(port, "/commands"), String.class);

    assertThat(getCommandsResponse.getStatusCode(), equalTo(HttpStatus.OK));
    ResourcesAssertions.assertThat(getCommandsResponse)
                       .hasSize("$", 7)
                       .contentRangeEquals("0-6/7")
                       .contentTypeEquals("application/json;charset=utf-8")
                       .contains("$[1].reference", "FR002")
                       .contains("$[1].details.href", getLocalURL(port, "/commands/FR002"))
                       .contains("$[1].details.rel", "command")
                       .contains("$[5].reference", "FR006")
                       .contains("$[5].details.href", getLocalURL(port, "/commands/FR006"))
                       .contains("$[5].details.rel", "command");
  }

  @Test
  public void get_commands_should_return_3_commands_from_index_2() throws Exception {
    int from = 2;
    int size = 3;
    when(mockCommandRepo.getCommands(from, size)).thenReturn(COMMANDS.subList(from, from + size));
    when(mockCommandRepo.count()).thenReturn((long) COMMANDS.size());

    ResponseEntity<String> getCommandsResponse = template.getForEntity(getLocalURL(port, "/commands?from=2&size=3"),
                                                                       String.class);

    ResourcesAssertions.assertThat(getCommandsResponse)
                       .statusIsOk()
                       .contentRangeEquals("2-4/7")
                       .contentTypeEquals("application/json;charset=utf-8")
                       .hasSize("$", size)
                       .contains("$[0].reference", "FR003")
                       .contains("$[0].details.href", getLocalURL(port, "/commands/FR003"))
                       .contains("$[0].details.rel", "command");
  }

  @Test
  public void get_commands_with_size_too_big_should_return_bad_request() throws Exception {

    ResponseEntity<String> getCommandsResponse = template
      .getForEntity(getLocalURL(port, "/commands?from=2&size=40000"), String.class);

    ResourcesAssertions.assertThat(getCommandsResponse)
                       .statusIsBadRequest()
                       .contentTypeEquals("text/plain;charset=utf-8")
                       .containsRaw("Maximum request size is 100");
  }
}