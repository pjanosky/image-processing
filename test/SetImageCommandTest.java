import static org.junit.Assert.assertEquals;

import controller.commands.SetImageCommand;
import model.Image;
import model.ImageExamples;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.RgbPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the SetImageCommandClass
 */
public class SetImageCommandTest {

  private ImageProcessingModel model;

  /**
   * Construct a new SetImageCommandTest initializing all example test data.
   */
  public SetImageCommandTest() {
    model = new ImageProcessingModelImpl();
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullType() {
    new SetImageCommand(null, new String[0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullArgs() {
    new SetImageCommand("rainbow", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullArgument() {
    String[] args = {null};
    new SetImageCommand("rainbow", args);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoInvalidTyp() {
    new SetImageCommand("no a real type", new String[0]).go(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoRainbowWrongNumArgs() {
    String[] args = {"2"};
    new SetImageCommand("rainbow", args).go(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoRainbowNoCurrentLayerSet() {
    String[] args = {"1", "1"};
    new SetImageCommand("rainbow", args).go(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoRainbowInvalidArgs() {
    String[] args = {"hello", "it's me"};
    new SetImageCommand("rainbow", args).go(model);
  }

  @Test
  public void testGoRainbowValid() {
    model.addLayer("layer1");
    String[] args = {"2", "2"};
    new SetImageCommand("rainbow", args).go(model);
    Image expected = ImageExamples.rainbow(2, 2);
    assertEquals(expected, model.getImageIn("layer1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoCheckerboardWrongNumArgs() {
    String[] args = {"2", "3"};
    new SetImageCommand("checkerboard", args).go(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoCheckerboardNoCurrentLayerSet() {
    String[] args = {"1", "1", "1"};
    new SetImageCommand("checkerboard", args).go(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoCheckerboardInvalidArgs() {
    String[] args = {"hello", "it's me", " "};
    new SetImageCommand("checkerboard", args).go(model);
  }

  @Test
  public void testGoCheckerboardValid() {
    model.addLayer("layer1");
    String[] args = {"2", "2", "2"};
    new SetImageCommand("checkerboard", args).go(model);
    Image expected = ImageExamples.checkerboard(2, 2, 2, 2,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 255, 255));
    assertEquals(expected, model.getImageIn("layer1"));
  }
}