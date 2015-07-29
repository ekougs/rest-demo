package com.zenika.restdemo.test;

import junit.framework.TestCase;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

/**
 * User: sennen
 * Date: 20/05/15
 * Time: 12:21
 */
public class HttpResponseAssertion {
  private static final String LOCATION = "Location";
  private final HttpStatus statusCode;
  private final HttpHeaders headers;

  public HttpResponseAssertion(ClientHttpResponse response) throws IOException {
    statusCode = response.getStatusCode();
    headers = response.getHeaders();
    // response.getBody() read it entirely
  }

  public HttpResponseAssertion noContent() {
     return statusEquals(204);
  }

  public HttpResponseAssertion created() {
    return statusEquals(201);
  }

  private HttpResponseAssertion statusEquals(int status) {
    TestCase.assertEquals(status, statusCode.value());
    return this;
  }

  public HttpResponseAssertion hasLocationTemplate(String locationTemplate) {
    TestCase.assertTrue(headers.containsKey(LOCATION));
    List<String> location = headers.get(LOCATION);
    Assertions.assertThat(location).hasSize(1);
    Assertions.assertThat(location.get(0)).matches(locationTemplate);
    return this;
  }
}
