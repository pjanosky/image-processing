import static org.junit.Assert.assertEquals;

import controller.JpegImportExporter;
import controller.PngImportExporter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import model.Image;
import controller.ImageImportExporter;
import org.junit.Test;

public class ArbitraryImportExporterTest {
  // JUST TO TEST WHEN I WAS WRITING THE CLASSES
  // delete later
  @Test
  public void sanityCheck() {
    ImageImportExporter ie = new JpegImportExporter();
    Image image = ImageExamples.rainbow(20, 2);

    try {
      OutputStream output = new FileOutputStream("rainbow.jpeg");
      ie.saveImage(output, image);
      ie = new PngImportExporter();
      output.close();

      output = new FileOutputStream("rainbow.png");
      ie.saveImage(output, image);
      output.close();
    } catch (IOException e) {
      System.out.println("Failed to save image: " + e.getMessage());
    }
  }

  @Test
  public void testPrintStream() {
    PrintStream out = new PrintStream(new FailOutputStream());
    out.print("hello world");
  }
}