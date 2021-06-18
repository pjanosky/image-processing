package controller;

/**
 * A factory class for ImageImporterExporters.
 */
public class ImportExporterCreator {

  /**
   * Generates a new ImageImporterExporter capable of saving images with the specified format.
   *
   * @param format a String representing the informal name of the image format.
   * @return the new ImageImporterExporter
   * @throws IllegalArgumentException if the image format is is not supported.
   */
  public static ImageImportExporter create(String format) throws IllegalArgumentException {
    switch (format.toLowerCase()) {
      case "jpeg":
        return new JpegImportExporter();
      case "png":
        return new PngImportExporter();
      case "ppm":
        return new PpmImportExporter();
      default:
        throw new IllegalArgumentException("Unsupported image format.");
    }
  }
}
