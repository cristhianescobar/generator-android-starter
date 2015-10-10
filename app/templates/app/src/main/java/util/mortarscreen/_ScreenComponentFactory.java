package <%= appPackage %>.util.mortarscreen;

public interface ScreenComponentFactory<T> {
    Object createComponent(T parent);
}
