package <%= appPackage %>.environment;

public class DevelopmentEnvironment extends Environment {

    public DevelopmentEnvironment() {
        super("Development");
    }

    @Override
    public String getMixpanelToken() {
        return "replace with your token";
    }

    @Override
    public String getApiHost() {
        return "http://appname-dev.appspot.com";
    }
}
