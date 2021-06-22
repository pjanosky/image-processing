import java.io.IOException;

/**
 * An implementation of Appendable that throws IOExceptions whenever data is appended. Intended to
 * be used for testing error handling.
 */
public class FailAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
