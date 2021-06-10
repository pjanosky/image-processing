package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ImageImportExporter {
  Image parseImage(InputStream input) throws IOException;

  void exportImage(OutputStream output, Image image) throws IOException;

}
