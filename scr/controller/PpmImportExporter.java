package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import model.Image;
import model.Image24Bit;
import model.Pixel;
import model.RgbPixel;

/**
 * Represents an image importer and exporter for PPM formatted files. This object will be called
 * when the scanned file is in PPM format.
 */
public class PpmImportExporter implements ImageImportExporter {

  @Override
  public Image parseImage(InputStream input) throws IOException {
    Scanner scanner = new Scanner(removeComments(input));

    // Parse file token
    String token;
    try {
      token = scanner.next();
    } catch (NoSuchElementException e) {
      throw new IOException("Failed to read ppm image token.");
    }
    if (!token.equals("P3")) {
      throw new IOException("Invalid PPM file: plain RAW file should begin with P3");
    }

    // Parse image data
    int width;
    int height;
    int maxValue;
    try {
      width = scanner.nextInt();
      height = scanner.nextInt();
      maxValue = scanner.nextInt();
    } catch (NoSuchElementException e) {
      throw new IOException("Failed to parse image data.");
    }

    // Parse pixel data
    Pixel[][] pixels = new Pixel[height][width];
    for (int row = 0; row < height; row += 1) {
      for (int col = 0; col < width; col += 1) {
        try {
          pixels[row][col] = new RgbPixel(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        } catch (NoSuchElementException e) {
          throw new IOException("Failed to parse pixel data.");
        }
      }
    }

    return createImage(pixels, maxValue);
  }

  /**
   * Removes all of the comments lines from an InputStream. A comment line is denoted with a '#' as
   * the first character on the line.
   *
   * @param input the input to remove comments from
   * @return the contents of the input as a String with all of the comment lines removed
   */
  private static String removeComments(InputStream input) {
    Scanner scanner = new Scanner(input);
    StringBuilder builder = new StringBuilder();

    // Remove comment lines
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.charAt(0) != '#') {
        builder.append(line).append(System.lineSeparator());
      }
    }
    return builder.toString();
  }

  /**
   * Creates a new image from a collection of pixels based on the max value specified in the image
   * file.
   *
   * @param pixels   the pixels to create the image with
   * @param maxValue the maximum value for colors in the image
   * @return the created image object
   * @throws IOException if the maximum value for colors is not supported
   */
  private static Image createImage(Pixel[][] pixels, int maxValue) throws IOException {
    if (maxValue == 255) {
      return new Image24Bit(pixels, false);
    } else {
      throw new IOException("Invalid bit number.");
    }
  }

  @Override
  public void saveImage(OutputStream output, Image image) throws IOException {
    StringBuilder content = new StringBuilder();

    content.append("P3").append(System.lineSeparator());
    content.append(image.getWidth()).append(" ");
    content.append(image.getHeight()).append(System.lineSeparator());
    content.append(255).append(System.lineSeparator());

    for (int row = 0; row < image.getHeight(); row += 1) {
      for (int col = 0; col < image.getWidth(); col += 1) {
        content.append(image.getPixelAt(row, col)).append(System.lineSeparator());
      }
    }

    output.write(content.toString().getBytes());
  }
}
