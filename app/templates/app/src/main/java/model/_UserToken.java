package <%= appPackage %>.model;

import java.io.UnsupportedEncodingException;

import android.util.Base64;

public class UserToken {
  private final String username;
  private final String token;

  public UserToken(String username, String token) {
    this.username = username;
    this.token = token;
  }

  @Override
  public String toString() {
    return String.format("Basic %s", encodeCredentials());
  }

  private String encodeCredentials() {
    byte[] data;
    try {
      String credentials = String.format("%s:%s", username, token);
      data = credentials.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Error encoding credentials: " + e.getMessage(), e);
    }
    return Base64.encodeToString(data, Base64.DEFAULT);
  }
}
