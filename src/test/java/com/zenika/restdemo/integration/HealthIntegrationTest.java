package com.zenika.restdemo.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static com.zenika.restdemo.test.ResourcesUtils.getLocalURL;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * User: sennen
 * Date: 13/05/15
 * Time: 16:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegrationTestConfiguration.class)
@WebIntegrationTest("server.port=0")
public class HealthIntegrationTest {
  private RestTemplate template = new TestRestTemplate();

  @Value("${local.server.port}")
  private int port;

  @Test
  public void get_health_should_return_string() {
    ResponseEntity<String> healthResponse = template.getForEntity(getLocalURL(port, "/health"), String.class);
    assertThat(healthResponse.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(healthResponse.getBody()).isEqualToIgnoringCase("Up and running");
  }
}
