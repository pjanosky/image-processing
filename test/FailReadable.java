import java.io.IOException;
import java.nio.CharBuffer;

/**
 * An implementation of Readable that throws IOExceptions every time data is read.
 * Intended to be used for testing error handling.
 */
public class FailReadable implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException();
  }
}
