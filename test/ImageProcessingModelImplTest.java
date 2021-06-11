import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.io.IOException;
import model.Image;
import model.ImageOperationCreator;
import model.ImageOperationCreator.IMGOperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.PpmImportExporter;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests the ImageProcessingModelImp class.
 */
public class ImageProcessingModelImplTest {

  // Tests the setImageMethod with a null Image.
  @Test(expected = IllegalArgumentException.class)
  public void testSetImageWithNullValue() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.setImage(null);
  }

  // Tests the setImageMethod with a valid Image.
  @Test
  public void testSetCurrentImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(1, 1);

    assertNull(exampleModel.getCurrentImage());
    exampleModel.setImage(exampleImage);
    assertEquals(exampleImage, exampleModel.getCurrentImage());
    assertEquals(exampleImage, exampleModel.getOriginalImage());
  }

  // Tests the applyOperation method with a null ImageOperation.
  @Test(expected = IllegalArgumentException.class)
  public void testApplyOperationWithNullValues() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(1, 1);
    exampleModel.setImage(exampleImage);

    exampleModel.applyOperation(null);
  }

  // Tests the applyOperation method with a sharpen operation.
  @Test
  public void testApplyOperationSharpen() {
    Image exampleImage = ImageExamples.rainbow(10, 2);

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.SHARPEN));

    assertEquals(new RgbPixel(225, 0, 0),
        exampleModel.getCurrentImage().getPixelAt(0, 0));
    assertEquals(new RgbPixel(225, 0, 0),
        exampleModel.getCurrentImage().getPixelAt(0, 9));
    assertEquals(new RgbPixel(240, 0, 225),
        exampleModel.getCurrentImage().getPixelAt(11, 0));
    assertEquals(new RgbPixel(240, 0, 225),
        exampleModel.getCurrentImage().getPixelAt(11, 9));
    assertEquals(new RgbPixel(225, 255, 0),
        exampleModel.getCurrentImage().getPixelAt(5, 0));
    assertEquals(new RgbPixel(0, 225, 0),
        exampleModel.getCurrentImage().getPixelAt(7, 0));
    assertEquals(new RgbPixel(225, 255, 0),
        exampleModel.getCurrentImage().getPixelAt(5, 9));
    assertEquals(new RgbPixel(0, 225, 0),
        exampleModel.getCurrentImage().getPixelAt(7, 9));
    assertEquals(new RgbPixel(255, 0, 225),
        exampleModel.getCurrentImage().getPixelAt(11, 6));
    assertEquals(new RgbPixel(225, 0, 0),
        exampleModel.getCurrentImage().getPixelAt(0, 6));
    assertEquals(new RgbPixel(255, 215, 0),
        exampleModel.getCurrentImage().getPixelAt(4, 6));
    assertEquals(new RgbPixel(200, 106, 0),
        exampleModel.getCurrentImage().getPixelAt(3, 7));

  }

  // Tests the applyOperation method with a blur operation.
  @Test
  public void testApplyOperationBlur() {
    Image exampleImage = ImageExamples.checkerboard(2, 2, 2, 2,
        new RgbPixel(255, 0, 0),
        new RgbPixel(0, 0, 255));

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.BLUR));

    assertEquals(new RgbPixel(143, 0, 0),
        exampleModel.getCurrentImage().getPixelAt(0, 0));
    assertEquals(new RgbPixel(143, 0, 47),
        exampleModel.getCurrentImage().getPixelAt(0, 1));
    assertEquals(new RgbPixel(47, 0, 143),
        exampleModel.getCurrentImage().getPixelAt(0, 2));
    assertEquals(new RgbPixel(0, 0, 143),
        exampleModel.getCurrentImage().getPixelAt(0, 3));
    assertEquals(new RgbPixel(143, 0, 47),
        exampleModel.getCurrentImage().getPixelAt(1, 0));
    assertEquals(new RgbPixel(159, 0, 95),
        exampleModel.getCurrentImage().getPixelAt(1, 1));
    assertEquals(new RgbPixel(95, 0, 159),
        exampleModel.getCurrentImage().getPixelAt(1, 2));
    assertEquals(new RgbPixel(47, 0, 143),
        exampleModel.getCurrentImage().getPixelAt(1, 3));
  }

  // Tests the applyOperation method with a greyscale operation.
  @Test
  public void testApplyOperationGreyscale() {
    Image exampleImage = ImageExamples.rainbow(1, 1);

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.GREYSCALE));

    assertEquals(new RgbPixel(42, 42, 42),
        exampleModel.getCurrentImage().getPixelAt(0, 0));
    assertEquals(new RgbPixel(96, 96, 96),
        exampleModel.getCurrentImage().getPixelAt(1, 0));
    assertEquals(new RgbPixel(185, 185, 185),
        exampleModel.getCurrentImage().getPixelAt(2, 0));
    assertEquals(new RgbPixel(143, 143, 143),
        exampleModel.getCurrentImage().getPixelAt(3, 0));
    assertEquals(new RgbPixel(14, 14, 14),
        exampleModel.getCurrentImage().getPixelAt(4, 0));
    assertEquals(new RgbPixel(48, 48, 48),
        exampleModel.getCurrentImage().getPixelAt(5, 0));
  }

  // Tests the applyOperation method with a sepia operation.
  @Test
  public void testApplyOperationSepia() {
    Image exampleImage = ImageExamples.rainbow(1, 1);

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.SEPIA));

    assertEquals(new RgbPixel(78, 69, 54),
        exampleModel.getCurrentImage().getPixelAt(0, 0));
    assertEquals(new RgbPixel(136, 121, 94),
        exampleModel.getCurrentImage().getPixelAt(1, 0));
    assertEquals(new RgbPixel(232, 207, 161),
        exampleModel.getCurrentImage().getPixelAt(2, 0));
    assertEquals(new RgbPixel(153, 137, 106),
        exampleModel.getCurrentImage().getPixelAt(3, 0));
    assertEquals(new RgbPixel(37, 33, 26),
        exampleModel.getCurrentImage().getPixelAt(4, 0));
    assertEquals(new RgbPixel(100, 89, 69),
        exampleModel.getCurrentImage().getPixelAt(5, 0));

  }

  // Tests the applyOperation method with a sepia operation followed by a greyscale operation.
  @Test
  public void testApplyOperationSepiaThenGreyscale() {
    Image exampleImage = ImageExamples.rainbow(1, 1);

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.SEPIA));
    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.GREYSCALE));

    assertEquals(new RgbPixel(69, 69, 69),
        exampleModel.getCurrentImage().getPixelAt(0, 0));
    assertEquals(new RgbPixel(122, 122, 122),
        exampleModel.getCurrentImage().getPixelAt(1, 0));
    assertEquals(new RgbPixel(208, 208, 208),
        exampleModel.getCurrentImage().getPixelAt(2, 0));
    assertEquals(new RgbPixel(138, 138, 138),
        exampleModel.getCurrentImage().getPixelAt(3, 0));
    assertEquals(new RgbPixel(33, 33, 33),
        exampleModel.getCurrentImage().getPixelAt(4, 0));
    assertEquals(new RgbPixel(89, 89, 89),
        exampleModel.getCurrentImage().getPixelAt(5, 0));
  }

  // Tests the exportImage method with a directory as a filepath.
  @Test(expected = IllegalArgumentException.class)
  public void testExportCurrentImageWithADirectoryAsFilePath() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(new PpmImportExporter(), "/res");
  }

  // Tests the exportImage method with a invalid null ImageImportExporter.
  @Test(expected = IllegalArgumentException.class)
  public void testExportCurrentImageWithNullValueAsImportExporter() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(null, "test/images/image.ppm");
  }

  // Tests the exportImage method with a invalid null filePath.
  @Test(expected = IllegalArgumentException.class)
  public void testExportCurrentImageWithNullValueAsFilePath() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(new PpmImportExporter(), null);
  }

  // Tests the exportImage method with a valid filePath.
  @Test
  public void testExportCurrentImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(10, 2);
    exampleModel.setImage(exampleImage);

    exampleModel.exportCurrentImage(new PpmImportExporter(), "test/images/image.ppm");

    Image savedImage = null;
    try {
      savedImage = new PpmImportExporter().parseImage(
          new FileInputStream("test/images/image.ppm"));
    } catch (IOException e) {
      fail("failed to read exported image");
    }
    assertEquals(exampleImage, savedImage);
  }

  // Tests the importImage method with a null ImageImportExporter.
  @Test(expected = IllegalArgumentException.class)
  public void testImportImageWithANullImportExporter() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(null, "test/images/image.ppm");
  }

  // Tests the importImage method with a null filePath.
  @Test(expected = IllegalArgumentException.class)
  public void testImportImageWithANullFilePath() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(new PpmImportExporter(), null);
  }

  // Tests the importImage method with a invalid filePath.
  @Test(expected = IllegalArgumentException.class)
  public void testImportImageWithANonexistentFilePath() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(new PpmImportExporter(), "image1.ppm");
  }

  // Tests the importImage method with a valid filePath and image.
  @Test
  public void testImportImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(10, 2);
    exampleModel.importImage(new PpmImportExporter(), "test/images/image.ppm");

    assertEquals(exampleImage, exampleModel.getCurrentImage());
  }

  // Tests the getCurrentImage method.
  @Test
  public void testGetCurrentImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(10, 2);
    exampleModel.setImage(exampleImage);

    assertEquals(exampleImage, exampleModel.getCurrentImage());
  }

  // Tests the getOriginalImage method.
  @Test
  public void testGetOriginalImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(10, 2);
    exampleModel.setImage(exampleImage);

    assertEquals(exampleModel.getOriginalImage(), exampleModel.getCurrentImage());
    assertEquals(exampleImage, exampleModel.getOriginalImage());
    assertEquals(exampleImage, exampleModel.getCurrentImage());

    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.BLUR));
    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.SEPIA));

    assertEquals(exampleImage, exampleModel.getOriginalImage());
  }

  // Tests the revert method.
  @Test
  public void testRevert() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(10, 2);
    exampleModel.setImage(exampleImage);

    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.BLUR));
    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.SEPIA));

    exampleModel.revert();
    assertEquals(exampleModel.getOriginalImage(), exampleModel.getCurrentImage());
    assertEquals(exampleImage, exampleModel.getOriginalImage());
    assertEquals(exampleImage, exampleModel.getCurrentImage());

  }

}