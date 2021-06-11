import static org.junit.Assert.*;

import model.Image;
import model.Image24Bit;
import model.ImageOperationCreator;
import model.ImageOperationCreator.IMGOperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.Pixel;
import model.RgbPixel;
import org.junit.Test;

public class ImageProcessingModelImplTest {

  @Test
  public void testSetCurrentImage() {
    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    Image exampleImage = ImageExamples.rainbow(1, 1);
//    Image exampleImage = new Image24Bit(new Pixel[][]{
//        {new RgbPixel(200, 0, 0),
//            new RgbPixel(200, 75, 0),
//            new RgbPixel(200, 200, 0)},
//        {new RgbPixel(0, 200, 0),
//            new RgbPixel(0, 0, 200),
//            new RgbPixel(160, 0, 200)}});

    assertEquals(null, exampleModel.getCurrentImage());
    exampleModel.setCurrentImage(exampleImage);
    assertEquals(exampleImage, exampleModel.getCurrentImage());
  }

  @Test
  public void testApplyOperationSharpen() {
    Image exampleImage = new Image24Bit(new Pixel[][]{
        {new RgbPixel(200, 0, 0),
            new RgbPixel(200, 75, 0),
            new RgbPixel(200, 200, 0)},
        {new RgbPixel(0, 200, 0),
            new RgbPixel(0, 0, 200),
            new RgbPixel(160, 0, 200)}});

    ImageProcessingModel exampleModel = new ImageProcessingModelImpl();
    exampleModel.setCurrentImage(exampleImage);
    exampleModel.applyOperation(
        new ImageOperationCreator().create(IMGOperationType.SHARPEN));



  }

}