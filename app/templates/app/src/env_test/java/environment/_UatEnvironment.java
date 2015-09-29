package <%= appPackage %>.environment;

public class UatEnvironment extends Environment {

    public UatEnvironment() {
        super("UAT");
    }

    @Override
    public String getMixpanelToken() {
        return "replace with your token";
    }

    @Override
    public String getApiHost() {
        return "http://appname-uat.appspot.com";
    }
}
