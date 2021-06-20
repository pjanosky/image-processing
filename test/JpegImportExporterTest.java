import static org.junit.Assert.*;

import controller.ImageImportExporter;
import controller.JpegImportExporter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import model.Image;
import org.junit.Test;

public class JpegImportExporterTest {

  private ImageImportExporter ie;

  /**
   * Constructs a new PngImportExporterTest initializing the example test data.
   */
  public JpegImportExporterTest() {
    ie = new JpegImportExporter();
  }

  @Test
  public void testSaveImage() {
    String path = "test/data/image.jpeg";
    Image image = ImageExamples.rainbow(10, 2);
    try {
      ie.saveImage(new FileOutputStream(path), image);
    } catch (IOException e) {
      fail(e.getMessage());
    }

    File file = new File(path);
    assertTrue(file.exists());
    assertTrue(file.isFile());
    assertEquals("image.jpeg", file.getName());
  }

  @Test
  public void testParseImage() {
    String path = "test/data/image.png";
    Image image = ImageExamples.rainbow(10, 2);
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    Image parsedImage = null;
    try {
      ie.saveImage(output, image);
      parsedImage = ie.parseImage(new ByteArrayInputStream(output.toByteArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
    assertEquals(image.getWidth(), parsedImage.getWidth());
    assertEquals(image.getHeight(), parsedImage.getHeight());
    for (int r = 0; r < image.getHeight(); r += 1) {
      for (int c = 0; c < image.getWidth(); c += 1) {
        assertTrue(image.getRedValueAt(r, c) - parsedImage.getRedValueAt(r, c) < 100);
        assertTrue(image.getGreenValueAt(r, c) - parsedImage.getGreenValueAt(r, c) < 100);
        assertTrue(image.getBlueValueAt(r, c) - parsedImage.getBlueValueAt(r, c) < 100);
      }
    }
  }
}