package com.zenika.restdemo.test;

import junit.framework.TestCase;
import org.hamcrest.Matchers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.JsonPathExpectationsHelper;

import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User: sennen
 * Date: 20/05/15
 * Time: 11:16
 */
public class ResponseEntityAssertion {
  private final ResponseEntity<String> responseEntity;

  public ResponseEntityAssertion(ResponseEntity<String> responseEntity) {
    this.responseEntity = responseEntity;
  }

  public ResponseEntityAssertion statusIsOk() {
    return statusEquals(200);
  }

  public ResponseEntityAssertion statusIsBadRequest() {
    return statusEquals(400);
  }

  public ResponseEntityAssertion noContent() {
    return statusEquals(204);
  }

  public ResponseEntityAssertion notFound() {
    return statusEquals(404);
  }

  public ResponseEntityAssertion statusEquals(int status) {
    TestCase.assertEquals(status, responseEntity.getStatusCode().value());
    return this;
  }

  public ResponseEntityAssertion hasSize(String jsonPath, int expectedSize) throws ParseException {
    JsonPathExpectationsHelper jsonPathExpectationsHelper = new JsonPathExpectationsHelper(jsonPath);
    jsonPathExpectationsHelper.assertValue(responseEntity.getBody(), Matchers.hasSize(expectedSize));
    return this;
  }

  public ResponseEntityAssertion contains(String jsonPath, Object expectedValue) throws ParseException {
    JsonPathExpectationsHelper jsonPathExpectationsHelper = new JsonPathExpectationsHelper(jsonPath);
    jsonPathExpectationsHelper.assertValue(responseEntity.getBody(), expectedValue);
    return this;
  }

  public ResponseEntityAssertion containsRaw(String expectedValue) throws ParseException {
    assertThat(responseEntity.getBody()).isEqualToIgnoringCase(expectedValue);
    return this;
  }

  public ResponseEntityAssertion contentRangeEquals(String expectedContentRange) {
    return checkSingleValueHeader(HttpHeaders.CONTENT_RANGE, expectedContentRange);
  }

  public ResponseEntityAssertion contentTypeEquals(String expectedContentType) {
    return checkSingleValueHeader(HttpHeaders.CONTENT_TYPE, expectedContentType);
  }

  private ResponseEntityAssertion checkSingleValueHeader(String header, String expectedHeaderValue) {
    List<String> actualHeaderValues = responseEntity.getHeaders().get(header);
    assertThat(actualHeaderValues).isNotEmpty().hasSize(1).contains(expectedHeaderValue);
    return this;
  }
}
