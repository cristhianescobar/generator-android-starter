package <%= appPackage %>.environment;

public class LocalEnvironment extends Environment {

    public LocalEnvironment() {
        super("Local");
    }

    @Override
    public String getMixpanelToken() {
        return "replace with your token";
    }

    @Override
    public String getApiHost() {
        return "http://localhost:8080";
    }
}
