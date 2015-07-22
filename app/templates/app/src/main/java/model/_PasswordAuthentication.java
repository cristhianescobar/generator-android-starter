package <%= appPackage %>.model;

public class PasswordAuthentication {
  private final String username;
  private final String password;

  public PasswordAuthentication(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return username;
  }
}
