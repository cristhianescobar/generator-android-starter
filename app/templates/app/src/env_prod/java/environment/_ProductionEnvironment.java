package <%= appPackage %>.environment;

public class ProductionEnvironment extends Environment {

	public ProductionEnvironment() {
		super("Production");
	}

	@Override
	public String getMixpanelToken() {
		return "replace with your token here";
	}

	@Override
	public String getApiHost() {
		return "http://example.org";
	}
}
