package <%= appPackage %>.util.flow;

import flow.path.Path;

/**
 * Created by pwner on 9/20/15.
 */
abstract public class BasePath extends Path {
    abstract public Object getDaggerModule();

    abstract public String getMortarScopeName();
}
