import java.io.IOException;
import java.io.InputStream;

/**
 * A InputStream implementation that throws an exception whenever data is read. Intended to be
 * used for testing how error handling.
 */
public class FailInputStream extends InputStream {

  @Override
  public int read() throws IOException {
    throw new IOException();
  }
}