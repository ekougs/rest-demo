package com.zenika.restdemo.test;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;

/**
 * User: sennen
 * Date: 20/05/15
 * Time: 11:15
 */
public class ResourcesAssertions {
  public static ResponseEntityAssertion assertThat(ResponseEntity<String> responseEntity) {
    return new ResponseEntityAssertion(responseEntity);
  }

  public static HttpResponseAssertionExtractor assertionExtractor() {
    return new HttpResponseAssertionExtractor();
  }

  private static class HttpResponseAssertionExtractor implements ResponseExtractor<HttpResponseAssertion> {

    @Override
    public HttpResponseAssertion extractData(ClientHttpResponse response) throws IOException {
      return new HttpResponseAssertion(response);
    }
  }
}
