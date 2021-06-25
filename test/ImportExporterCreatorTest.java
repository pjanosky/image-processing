import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.ImageImportExporter;
import controller.ImportExporterCreator;
import controller.JpegImportExporter;
import controller.PngImportExporter;
import controller.PpmImportExporter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import model.Image;
import model.ImageExamples;
import org.junit.Test;

/**
 * Tests the ImportExporterCreator class.
 */
public class ImportExporterCreatorTest {

  private final Image image;

  public ImportExporterCreatorTest() {
    image = ImageExamples.rainbow(10, 12);
  }

  @Test
  public void testCreateJpeg() {
    ImageImportExporter ie = ImportExporterCreator.create("jpeg");
    String path = "test/data/image.jpeg";

    Image parsedImage = null;
    try {
      new JpegImportExporter().saveImage(new FileOutputStream(path), image);
      parsedImage = ie.parseImage(new FileInputStream(path));
    } catch (IOException e) {
      fail("Failed to save and parse images. " + e.getMessage());
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

  @Test
  public void testCreatePng() {
    ImageImportExporter ie = ImportExporterCreator.create("png");
    String path = "test/data/image.png";

    Image parsedImage = null;
    try {
      new PngImportExporter().saveImage(new FileOutputStream(path), image);
      parsedImage = ie.parseImage(new FileInputStream(path));
    } catch (IOException e) {
      fail("Failed to save and parse images. " + e.getMessage());
    }
    assertEquals(image, parsedImage);
  }

  @Test
  public void testCreatePpm() {
    ImageImportExporter ie = ImportExporterCreator.create("ppm");
    String path = "test/data/image.ppm";

    Image parsedImage = null;
    try {
      new PpmImportExporter().saveImage(new FileOutputStream(path), image);
      parsedImage = ie.parseImage(new FileInputStream(path));
    } catch (IOException e) {
      fail("Failed to save and parse images. " + e.getMessage());
    }
    assertEquals(image, parsedImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFormat() {
    ImportExporterCreator.create("not a real format :)");
  }
}