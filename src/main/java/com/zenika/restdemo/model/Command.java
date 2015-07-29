package com.zenika.restdemo.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * User: sennen
 * Date: 27/07/15
 * Time: 14:30
 */
@Entity(value = "commands", noClassnameStored = true)
public class Command {
  @Id
  private String reference;

  public Command() {
  }

  public Command(String reference) {
    this.reference = reference;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }
}
