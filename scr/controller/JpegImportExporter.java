package controller;

/**
 * Represents an image importer and exporter for JPEG formatted files. JPEG is not a lossless
 * format, so import and exporting images may result in some inaccuracies.
 */
public class JpegImportExporter extends ArbitraryImportExporter {

  /**
   * Constructs a new JpegImportExporter object.
   */
  public JpegImportExporter() {
    super("jpeg");
  }
}
