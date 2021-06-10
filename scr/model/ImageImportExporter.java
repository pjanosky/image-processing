package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents and encapsulates different types of image import/exporters. Each class which
 * implements this interface will take in different file formats and import or export accordingly.
 */
public interface ImageImportExporter {

  /**
   * Reads the file path from the user and imports the image as a {@code Image} format.
   *
   * @param input the file path scanned from the user
   * @return an imported image
   * @throws IOException if the to-be-imported image file fails to be parsed.
   */
  Image parseImage(InputStream input) throws IOException;

  /**
   *
   * @param output
   * @param image
   * @throws IOException
   */
  void exportImage(OutputStream output, Image image) throws IOException;

}
