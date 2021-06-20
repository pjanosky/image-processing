package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import model.Image;
import model.Image24Bit;
import model.Pixel;
import model.RgbPixel;

/**
 * Represents an image importer and exporter for common image file types. Supports all file types
 * that {@code java.javax.imageio.ImageIO} {@code write} method supports.
 */
public abstract class ArbitraryImportExporter implements ImageImportExporter {

  private final String format;

  /**
   * Constructs a new ArbitraryImportExporter that imports and export files of a single format.
   *
   * @param format a string representing the informal name of a file format. Supported formats are
   *               the same as the {@code java.javax.imageio.ImageIO} {@code write} method.
   */
  protected ArbitraryImportExporter(String format) {
    this.format = format;
  }

  @Override
  public Image parseImage(InputStream input) throws IOException {
    if (input == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }
    BufferedImage image = ImageIO.read(input);
    if (image == null) {
      throw new IOException("Failed to parse image.");
    }

    Pixel[][] pixels = new Pixel[image.getHeight()][image.getWidth()];
    for (int r = 0; r < image.getHeight(); r += 1) {
      for (int c = 0; c < image.getWidth(); c += 1) {
        pixels[r][c] = getPixel(image.getRGB(c, r));
      }
    }
    return new Image24Bit(pixels);
  }

  @Override
  public void saveImage(OutputStream output, Image image) throws IOException {
    if (output == null || image == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    BufferedImage buffer = new BufferedImage(image.getWidth(),
        image.getHeight(), BufferedImage.TYPE_INT_BGR);
    for (int r = 0; r < image.getHeight(); r += 1) {
      for (int c = 0; c < image.getWidth(); c += 1) {
        buffer.setRGB(c, r, getRgb(image.getPixelAt(r, c)));
      }
    }

    ImageIO.write(buffer, this.format, output);
  }

  /**
   * Converts the red, green, and blue values of a {@link Pixel} to a single RGB value. Bits 0-7
   * represent the blue value. Bits 8-15 are green. Bits 16-23 are red.
   *
   * @param pixel the pixel to calculate the RGB value of.
   * @return the rgb value of the given pixel.
   */
  private int getRgb(Pixel pixel) {
    return pixel.getBlueValue() + (pixel.getGreenValue() << 8) + (pixel.getRedValue() << 16);
  }

  /**
   * Converts a single RGB value to a Pixel.
   *
   * @param rgb the RGB value to convert. Bits 0-7 represent the blue value. Bits 8-15 are green.
   *            Bits 16-23 are red.
   * @return the pixel representing the equivalent color the the given RGB value;
   */
  private Pixel getPixel(int rgb) {
    int blue = rgb & 0xFF; // first 8 bits
    int green = (rgb & 0xFF00) >> 8; // second 8 bits
    int red = (rgb & 0xFF0000) >> 16; // third 8 bits;
    return new RgbPixel(red, green, blue);
  }
}
