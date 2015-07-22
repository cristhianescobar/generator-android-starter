package <%= appPackage %>.model;

import org.hibernate.validator.constraints.NotBlank;

public class UserWithPassword extends User {
	@NotBlank(message = "Please provide a password")
	private final String password;

	public UserWithPassword(String name, String email, String password) {
		super(name, email);

		this.password = password;
	}
}
