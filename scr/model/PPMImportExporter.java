package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents an image importer and exporter for PPM formatted files. This object will be called
 * when the scanned file is in PPM format.
 */
public class PPMImportExporter implements ImageImportExporter {

  @Override
  public Image parseImage(InputStream input) throws IOException {
    Scanner scanner = new Scanner(input);
    StringBuilder builder = new StringBuilder();

    // Remove comment lines
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.charAt(0) != '#') {
        builder.append(line).append(System.lineSeparator());
      }
    }

    // Read file content
    scanner = new Scanner(builder.toString());

    // Parse file type
    String token = scanner.next();
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
          pixels[row][col] = new Pixel(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        } catch (NoSuchElementException e) {
          throw new IOException("Failed to parse pixel data.");
        }
      }
    }

    // Create image
    switch (maxValue) {
      case 255:
        return new Image24Bit(pixels, false);
      default:
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
