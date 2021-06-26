import org.junit.Before;
import org.junit.Test;
import view.GuiImageProcessingView;
import view.GuiView;

/**
 * Tests a limited subset of the view's functionality. Mostly tests error
 */
public class GuiViewTest {

  private GuiImageProcessingView view;

  /**
   * Constructs a new GuiViewTest, initializing all example test data.
   */
  public GuiViewTest() {
    view = new GuiView();
  }

  @Before
  public void setup() {
    view = new GuiView();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderMessageNullMessage() {
    view.renderMessage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderErrorNullMessage() {
    view.renderError(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCommandListenerNullListener() {
    view.setCommandListener(null);
  }
}