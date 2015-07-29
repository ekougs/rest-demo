package com.zenika.restdemo.dto;

/**
 * User: sennen
 * Date: 21/05/15
 * Time: 11:07
 */
public class Hyperlink {
  private String href;
  private String rel;

  public Hyperlink() {
  }

  public Hyperlink(String href, String rel) {
    this.href = href;
    this.rel = rel;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getRel() {
    return rel;
  }

  public void setRel(String rel) {
    this.rel = rel;
  }
}
