import static org.junit.Assert.assertEquals;

import model.Image;
import model.ImageOperation;
import model.ImageOperationCreator;
import model.ImageOperationCreator.IMGOperationType;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests the ImageOperationCreator factory class.
 */
public class ImageOperationCreatorTest {

  private final Image image;

  /**
   * Constructs a new ImageOperationCreateTest initializing all example data for testing.
   */
  public ImageOperationCreatorTest() {
    this.image = ImageExamples.checkerboard(2, 2, 1, 1,
        new RgbPixel(245, 160, 60),
        new RgbPixel(240, 100, 230));
  }

  // Test the create method with a null type
  @Test(expected = IllegalArgumentException.class)
  public void testCreateNullType() {
    ImageOperationCreator.create(null);
  }

  // Test the create method with a blur type.
  @Test
  public void testCreateBlur() {
    ImageOperation op = ImageOperationCreator.create(IMGOperationType.BLUR);
    Image applied = op.apply(image);

    assertEquals(new RgbPixel(136, 75, 76), applied.getPixelAt(0, 0));
    assertEquals(new RgbPixel(136, 71, 86), applied.getPixelAt(0, 1));
    assertEquals(new RgbPixel(136, 71, 86), applied.getPixelAt(1, 0));
    assertEquals(new RgbPixel(136, 75, 76), applied.getPixelAt(1, 1));
  }

  // Test the create method with a sharpen type.
  @Test
  public void testCreateSharpen() {
    ImageOperation op = ImageOperationCreator.create(IMGOperationType.SHARPEN);
    Image image = ImageExamples.checkerboard(2, 2, 1, 1,
        new RgbPixel(245, 160, 60),
        new RgbPixel(240, 100, 230));
    Image applied = op.apply(image);

    assertEquals(new RgbPixel(255, 250, 190), applied.getPixelAt(0, 0));
    assertEquals(new RgbPixel(255, 205, 255), applied.getPixelAt(0, 1));
    assertEquals(new RgbPixel(255, 205, 255), applied.getPixelAt(1, 0));
    assertEquals(new RgbPixel(255, 250, 190), applied.getPixelAt(1, 1));
  }

  // Test the create method with a greyscale type.
  @Test
  public void testCreateGreyscale() {
    ImageOperation op = ImageOperationCreator.create(IMGOperationType.GREYSCALE);
    Image applied = op.apply(image);

    assertEquals(new RgbPixel(170, 170, 170), applied.getPixelAt(0, 0));
    assertEquals(new RgbPixel(139, 139, 139), applied.getPixelAt(0, 1));
    assertEquals(new RgbPixel(139, 139, 139), applied.getPixelAt(1, 0));
    assertEquals(new RgbPixel(170, 170, 170), applied.getPixelAt(1, 1));
  }

  // Test the create method with a sepia type.
  @Test
  public void testCreateSepia() {
    ImageOperation op = ImageOperationCreator.create(IMGOperationType.SEPIA);
    Image applied = op.apply(image);

    assertEquals(new RgbPixel(230, 205, 159), applied.getPixelAt(0, 0));
    assertEquals(new RgbPixel(214, 191, 148), applied.getPixelAt(0, 1));
    assertEquals(new RgbPixel(214, 191, 148), applied.getPixelAt(1, 0));
    assertEquals(new RgbPixel(230, 205, 159), applied.getPixelAt(1, 1));
  }
}