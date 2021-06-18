import java.io.IOException;
import java.io.OutputStream;

/**
 * An OutputStream implementation the throws an exception whenever data is written. Intended to be
 * used for testing error handling.
 */
public class FailOutputStream extends OutputStream {

  @Override
  public void write(int b) throws IOException {
    throw new IOException();
  }
}