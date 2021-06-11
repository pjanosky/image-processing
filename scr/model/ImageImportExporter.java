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
   * parses an {@code Image} object from image file data.
   *
   * @param input an InputStream from which the image data is read
   * @return the parsed {@code Image} object
   * @throws IOException if the image cannot be parse from the input stream due to an error reading
   *                     data or parsing the data into an {@code Image} object.
   */
  Image parseImage(InputStream input) throws IOException;

  /**
   * Writes the contents of an {@code Image} object to an output.
   *
   * @param output the OutputStream to write the image data to
   * @param image  the image to write
   * @throws IOException if the Image data cannot be written to the Output stream.
   */
  void saveImage(OutputStream output, Image image) throws IOException;

}
