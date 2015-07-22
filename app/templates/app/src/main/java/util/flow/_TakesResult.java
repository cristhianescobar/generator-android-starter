package <%= appPackage %>.util.flow;

public interface TakesResult<T> {
	void receive(T result);
}
