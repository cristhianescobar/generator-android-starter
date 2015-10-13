package <%= appPackage %>.util.mortarscreen;

import android.content.Context;

import <%= appPackage %>.util.dagger.DaggerService;

import flow.path.Path;
import mortar.MortarScope;


public class ScreenScoper {
    public MortarScope getScreenScope(Context context, String name, Path path) {
        MortarScope parentScope = MortarScope.getScope(context);

        MortarScope childScope = parentScope.findChild(name);
        if (childScope != null) {
            return childScope;
        }

        if (!(path instanceof ScreenComponentFactory)) {
            throw new IllegalStateException("Path must imlement ComponentFactory");
        }
        ScreenComponentFactory screenComponentFactory = (ScreenComponentFactory) path;
        Object component = screenComponentFactory.createComponent(parentScope.getService(DaggerService.SERVICE_NAME));

        MortarScope.Builder builder = parentScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, component);

        return builder.build(name);
    }
}
