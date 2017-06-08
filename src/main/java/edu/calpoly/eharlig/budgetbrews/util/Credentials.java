package edu.calpoly.eharlig.budgetbrews.util;

public class Credentials {
  private static final String AWS_KEY = "";
  private static final String SECRET_KEY = "";
  private static final String EMAIL = "budgetbrews305@gmail.com";
  
  private Credentials() {}

  public static String getAwsKey() {
    return AWS_KEY;
  }
  public static String getSecretKey() {
    return SECRET_KEY;
  }
  public static String getEmail() {
    return EMAIL;
  }
}
