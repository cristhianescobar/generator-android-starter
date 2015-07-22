package <%= appPackage %>.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

public class User {
  @NotBlank(message = "Please provide your name")
  private String name;

  @NotBlank(message = "Please provide your email address")
  @Email(message = "Please provide a valid email address")
  private String email;

  private DateTime created;
  private DateTime modified;
  private DateTime lastLogin;

  User() {
  }

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public DateTime getCreated() {
    return created;
  }

  public DateTime getModified() {
    return modified;
  }

  public DateTime getLastLogin() {
    return lastLogin;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("name", name)
      .append("email", email)
      .append("created", created)
      .append("modified", modified)
      .append("lastLogin", lastLogin)
      .toString();
  }
}
