package <%= appPackage %>.environment;

public abstract class Environment {

	private final String name;

	public Environment(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract String getMixpanelToken();

	public abstract String getApiHost();

	public String getApiBasePath() {
		return "/api/v1/";
	}
}
