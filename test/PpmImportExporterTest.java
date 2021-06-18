import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import model.Image;
import model.Image24Bit;
import controller.ImageImportExporter;
import model.Pixel;
import model.RgbPixel;
import controller.PpmImportExporter;
import org.junit.Test;

/**
 * Tests the PpmImportExporterTest class.
 */
public class PpmImportExporterTest {

  private final ImageImportExporter ie;

  /**
   * Creates a new PpmImportExporterTest object initializing all example data used for testing.
   */
  public PpmImportExporterTest() {
    this.ie = new PpmImportExporter();
  }

  // Tests parsing a checkerboard image from an image data input stream.
  @Test
  public void testParseCheckerBoardImage() {
    Image expectedImage = ImageExamples.checkerboard(2, 4,
        1, 1,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 0, 0));

    StringBuilder inputData = new StringBuilder();
    inputData.append("P3").append(System.lineSeparator());
    inputData.append("4 2").append(System.lineSeparator());
    inputData.append("255").append(System.lineSeparator());
    inputData.append("0 0 0").append(System.lineSeparator());
    inputData.append("255 0 0").append(System.lineSeparator());
    inputData.append("0 0 0").append(System.lineSeparator());
    inputData.append("255 0 0").append(System.lineSeparator());
    inputData.append("255 0 0").append(System.lineSeparator());
    inputData.append("0 0 0").append(System.lineSeparator());
    inputData.append("255 0 0").append(System.lineSeparator());
    inputData.append("0 0 0").append(System.lineSeparator());
    InputStream input = new ByteArrayInputStream(inputData.toString().getBytes());

    Image actualImage = parseImage(input);

    assertEquals(expectedImage, actualImage);
  }

  // Tests saving a rainbow image to an output steam
  @Test
  public void testSaveRainbow() {
    Image image = ImageExamples.rainbow(1, 1);
    OutputStream output = new ByteArrayOutputStream();

    saveImage(output, image);

    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("P3").append(System.lineSeparator());
    expectedOutput.append("1 6").append(System.lineSeparator());
    expectedOutput.append("255").append(System.lineSeparator());
    expectedOutput.append("200 0 0").append(System.lineSeparator());
    expectedOutput.append("200 75 0").append(System.lineSeparator());
    expectedOutput.append("200 200 0").append(System.lineSeparator());
    expectedOutput.append("0 200 0").append(System.lineSeparator());
    expectedOutput.append("0 0 200").append(System.lineSeparator());
    expectedOutput.append("160 0 200").append(System.lineSeparator());

    assertEquals(expectedOutput.toString(), output.toString());
  }

  // Tests the an image is preserved when saving and parsing.
  @Test
  public void testSaveParseImage() {
    Image original = ImageExamples.rainbow(20, 2);
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    saveImage(output, original);

    InputStream input = new ByteArrayInputStream(output.toByteArray());
    Image saved = parseImage(input);

    assertEquals(original, saved);
  }

  // Test saving an image where writing to the OutputStream throws an exception
  @Test(expected = IOException.class)
  public void testSaveImageFailWriteOutput() throws IOException {
    Image image = new Image24Bit(new Pixel[][]{{new RgbPixel(0, 0, 0)}});
    OutputStream output = new FailOutputStream();
    ie.saveImage(output, image);
  }

  // Test parsing an image where reading from the InputStream throws an exception
  @Test(expected = IOException.class)
  public void testParseImageFailReadInput() throws IOException {
    InputStream input = new FailInputStream();
    ie.parseImage(input);
  }

  /**
   * Saves an image to an output stream, failing the test if the image cannot be written.
   *
   * @param output the output stream to save the image to
   * @param image  the image to save
   */
  private void saveImage(OutputStream output, Image image) {
    try {
      ie.saveImage(output, image);
    } catch (IOException e) {
      fail("Failed to save image to output stream.");
    }
  }

  /**
   * Parses a image from an input stream, failing the test if the image cannot be read.
   *
   * @param input the input stream containing the image data
   * @return the parsed image object
   */
  private Image parseImage(InputStream input) {
    try {
      return ie.parseImage(input);
    } catch (IOException e) {
      fail("Failed to read image from input stream.");
      return null;
    }
  }
}