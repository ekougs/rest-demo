package com.zenika.restdemo.test;

/**
 * User: sennen
 * Date: 29/07/15
 * Time: 15:05
 */
public class ResourcesUtils {
  public static String getLocalURL(int port, String path) {
    return String.format("http://localhost:%d%s", port, path);
  }

}
