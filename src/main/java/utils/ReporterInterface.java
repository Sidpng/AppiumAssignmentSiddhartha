package utils;

public interface ReporterInterface {

	void flush();

	void pass(String message);

	void fail(String message);

	void fail(Throwable t);

	void log(String message);

	void error(String message);

	void warn(String message);

}
