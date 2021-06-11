import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import model.Image;
import model.ImageImportExporter;
import model.ImageOperationCreator;
import model.ImageOperationCreator.IMGOperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.PpmImportExporter;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests all the methods from ImageProcessingModelImpl class
 */
public class ImageProcessingModelImplTest {

  //tests if an illegal argument exception is thrown a null value as the image has been passed
  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentImageWithNullValue() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.setImage(null);
  }

  //tests if the model sets the given image as the current image within th model
  @Test
  public void testSetCurrentImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(1, 1);

    assertEquals(null, exampleModel.getCurrentImage());
    exampleModel.setImage(exampleImage);
    assertEquals(exampleImage, exampleModel.getCurrentImage());
    assertEquals(exampleImage, exampleModel.getOriginalImage());
  }

  //tests if an illegal argument exception is thrown when a null value was passed as the operation
  // parameter
  @Test(expected = IllegalArgumentException.class)
  public void testApplyOperationWithNullValues() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(1, 1);
    exampleModel.setImage(exampleImage);

    exampleModel.applyOperation(null);
  }

  //tests the sharpen operation on the image
  @Test
  public void testApplyOperationSharpen() {
    Image exampleImage = ImageExamples.rainbow(10, 2);

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(
        new ImageOperationCreator().create(IMGOperationType.SHARPEN));


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

  //tests the blur operation on the image
  @Test
  public void testApplyOperationBlur() {
    Image exampleImage = ImageExamples.checkerboard(2, 2, 2, 2,
        new RgbPixel(255, 0, 0),
        new RgbPixel(0, 0, 255));

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(
        new ImageOperationCreator().create(IMGOperationType.BLUR));


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

  //tests the greyscale operation on the image
  @Test
  public void testApplyOperationGreyscale() {
    Image exampleImage = ImageExamples.rainbow(1, 1);

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(
        new ImageOperationCreator().create(IMGOperationType.GREYSCALE));

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

  //tests the sepia operation on the image
  @Test
  public void testApplyOperationSepia() {
    Image exampleImage = ImageExamples.rainbow(1, 1);

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(
        new ImageOperationCreator().create(IMGOperationType.SEPIA));

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

  //tests the greyscale operation on the image
  @Test
  public void testApplyOperationSepiaThenGreyscale() {
    Image exampleImage = ImageExamples.rainbow(1, 1);

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setImage(exampleImage);
    exampleModel.applyOperation(
        new ImageOperationCreator().create(IMGOperationType.SEPIA));
    exampleModel.applyOperation(
        new ImageOperationCreator().create(IMGOperationType.GREYSCALE));

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

  @Test(expected = IllegalArgumentException.class)
  public void testExportCurrentImageWithADirectoryAsFilePath() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(new PpmImportExporter(), "/res");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportCurrentImageWithNullValueAsImportExporter() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(null, "image.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportCurrentImageWithNullValueAsFilePath() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(new PpmImportExporter(), null);
  }

  @Test
  public void testExportCurrentImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(10,2);
    exampleModel.setImage(exampleImage);

    exampleModel.exportCurrentImage(new PpmImportExporter(), "image.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageWithANullImportExporter() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(null, "image.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageWithANullFilePath() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(new PpmImportExporter(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportImageWithANonexistentFilePath() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();

    exampleModel.importImage(new PpmImportExporter(), "image1.ppm");
  }

  @Test
  public void testImportImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(10,2);
    exampleModel.importImage(new PpmImportExporter(), "image.ppm");

    assertEquals(exampleImage, exampleModel.getCurrentImage());
  }

  @Test
  public void testGetCurrentImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(10,2);
    exampleModel.setImage(exampleImage);

    assertEquals(exampleImage, exampleModel.getCurrentImage());
  }

  @Test
  public void testGetOriginalImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(10, 2);
    exampleModel.setImage(exampleImage);

    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.BLUR));
    exampleModel.applyOperation(ImageOperationCreator.create(IMGOperationType.SEPIA));

    assertEquals(exampleImage, exampleModel.getOriginalImage());
  }

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