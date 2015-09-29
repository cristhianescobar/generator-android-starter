package <%= appPackage %>.screen.<%= screenPackage %>;

import <%= appPackage %>.ActivityModule;
import <%= appPackage %>.R;
import <%= appPackage %>.util.flow.BasePath;
import <%= appPackage %>.util.flow.Layout;
import <%= appPackage %>.util.mortarscreen.WithModule;

@WithModule(<%= screenName %>Screen.<%= screenName %>Module.class)
@Layout(R.layout.view_<%= screenPackage %>)
public class <%= screenName %>Screen extends BasePath {

    @Override
    public Object getDaggerModule() {
        return new <%= screenName %>Module();
    }

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @dagger.Module(
            injects = <%= screenName %>View.class,
            addsTo = ActivityModule.class
    )
    public class <%= screenName %>Module {
    }
}
