package com.zenika.restdemo.dto;

/**
 * User: sennen
 * Date: 27/07/15
 * Time: 11:51
 */
public class SimpleCommand {
  private final String reference;
  private final Hyperlink details;

  public SimpleCommand(String reference, Hyperlink details) {
    this.reference = reference;
    this.details = details;
  }

  public String getReference() {
    return reference;
  }

  public Hyperlink getDetails() {
    return details;
  }
}
