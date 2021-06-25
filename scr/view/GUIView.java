package view;

import controller.CommandListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.DownscaleOperation;
import model.Image;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.ImageProcessingModelState;
import model.Pixel;

public class GUIView extends JFrame implements GUIImageProcessingView {

  // Data:
  ImageProcessingModelState model;

  // Panels:
  // main panel
  JSplitPane mainSplitPlane;

  // image panel
  JPanel imagePanel;
  JScrollPane imageScrollPane;
  JLabel imageLabel;
  JLabel imageCaption;
  Icon imageIcon;

  // layers panel
  JPanel layersPanel;
  JScrollPane layersScrollPane;
  ButtonGroup layerButtons;


  // Menus:
  // file menu
  JMenu fileMenu;
  JMenuItem loadMenuItem;
  JMenuItem loadAllMenuItem;
  JMenuItem saveMenuItem;
  JMenuItem saveAllMenuItem;
  JMenu presetImageMenu;
  JMenuItem rainbowMenuItem;
  JMenuItem checkerboardMenuItem;
  JMenuItem runBatchScriptMenuItem;

  // layers menu
  JMenu layerMenu;
  JMenuItem addMenuItem;
  JMenuItem removeMenuItem;
  JMenuItem showMenuItem;
  JMenuItem hideMenuItem;
  JMenu currentMenu;
  ButtonGroup currentLayerMenuButtons;
  Function<String, ActionListener> currentListenerCreator;
  JMenuItem moveMenuItem;

  // image processing menu
  JMenu imageProcessMenu;
  JMenuItem blurImageItem;
  JMenuItem sharpenImageItem;
  JMenuItem greyscaleImageItem;
  JMenuItem sepiaImageItem;
  JMenuItem downscaleImageItem;


  public GUIView(ImageProcessingModelState model) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Model must not be null.");
    }
    this.model = model;

    setName("Image Processing");
    setSize(600, 400);
    setMinimumSize(new Dimension(400, 250));
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setupMenu();
    setupPanel();
  }

  /**
   * Configures the menu for this view and adds it to the frame. Called from the constructor. All of
   * the functionality of the image processing program should be exposed through the menu.
   */
  private void setupMenu() {
    // initialize menu bar
    JMenuBar menuBar = new JMenuBar();

    // File Menu (load, save, loadall, saveall)
    fileMenu = new JMenu("File");

    loadMenuItem = new JMenuItem("Load Current Layer");
    loadAllMenuItem = new JMenuItem("Load All layers");
    saveMenuItem = new JMenuItem("Save Current Layer");
    saveAllMenuItem = new JMenuItem("Save All Layers");
    presetImageMenu = new JMenu("Load Preset Image");
    rainbowMenuItem = new JMenuItem("Rainbow");
    checkerboardMenuItem = new JMenuItem("Checkerboard");
    presetImageMenu.add(rainbowMenuItem);
    presetImageMenu.add(checkerboardMenuItem);
    runBatchScriptMenuItem = new JMenuItem("Run Batch Script File");

    fileMenu.add(loadMenuItem);
    fileMenu.add(loadAllMenuItem);
    fileMenu.add(saveMenuItem);
    fileMenu.add(saveAllMenuItem);
    fileMenu.add(presetImageMenu);
    fileMenu.add(runBatchScriptMenuItem);

    menuBar.add(fileMenu);
    // TODO: initialize menu item for loading programmatic images

    // Layers Menu (add, remove, hide/show, current)
    layerMenu = new JMenu("Layers");

    addMenuItem = new JMenuItem("Add Layer");
    removeMenuItem = new JMenuItem("Remove Current Layer");
    showMenuItem = new JMenuItem("Show Current Layer");
    hideMenuItem = new JMenuItem("Hide Current Layer");
    moveMenuItem = new JMenuItem("Move Current Layer");
    currentMenu = new JMenu("Current Layer");
    currentLayerMenuButtons = new ButtonGroup();

    layerMenu.add(addMenuItem);
    layerMenu.add(removeMenuItem);
    layerMenu.add(hideMenuItem);
    layerMenu.add(showMenuItem);
    layerMenu.add(moveMenuItem);
    layerMenu.add(currentMenu);

    menuBar.add(layerMenu);

    // Image Processing Menu (blur, sharpen, sepia, greyscale)
    imageProcessMenu = new JMenu("Image Processing");

    blurImageItem = new JMenuItem("Blur Current Layer");
    sharpenImageItem = new JMenuItem("Sharpen Current Layer");
    sepiaImageItem = new JMenuItem("Sepia Current Layer");
    greyscaleImageItem = new JMenuItem("Greyscale Current Layer");
    downscaleImageItem = new JMenuItem("Downscale All Images");

    imageProcessMenu.add(blurImageItem);
    imageProcessMenu.add(sharpenImageItem);
    imageProcessMenu.add(sepiaImageItem);
    imageProcessMenu.add(greyscaleImageItem);
    imageProcessMenu.add(downscaleImageItem);

    menuBar.add(imageProcessMenu);

    // Update and set the menu bar
    this.setJMenuBar(menuBar);
  }

  /**
   * Configures the main panel for this view and adds it to the frame. Called from the constructor.
   */
  private void setupPanel() {
    // main panel
    mainSplitPlane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    // image panel
    imagePanel = new JPanel();
    imagePanel.setLayout(new BorderLayout());
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imageScrollPane = new JScrollPane(imageLabel);
    imagePanel.add(imageScrollPane, BorderLayout.CENTER);
    imageCaption = new JLabel();
    imageCaption.setHorizontalAlignment(JLabel.CENTER);
    imagePanel.add(imageCaption, BorderLayout.SOUTH);
    imagePanel.setMinimumSize(new Dimension(250, 150));
    mainSplitPlane.setLeftComponent(imagePanel);

    // layers panel
    layersPanel = new JPanel();
    layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
    layersPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    layerButtons = new ButtonGroup();
    layersScrollPane = new JScrollPane(layersPanel);
    layersScrollPane.setMinimumSize(new Dimension(150, 150));
    mainSplitPlane.setRightComponent(layersScrollPane);

    // add to the view
    mainSplitPlane.setDividerLocation(400);
    add(mainSplitPlane);
  }

  public void renderLayers(ImageProcessingModelState model) {
    updateImage(model);
    updateLayers(model);
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(mainSplitPlane, message, "", JOptionPane.PLAIN_MESSAGE);
  }

  public void renderError(String message) {
    JOptionPane.showMessageDialog(mainSplitPlane, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void addCommandListener(CommandListener listener) {
    // File menu
    loadMenuItem.addActionListener(evt -> {
      listener.load(chooseImage(true));
    });
    loadAllMenuItem.addActionListener(evt -> {
      listener.loadLayers(chooseDirectory(true));
    });
    saveMenuItem.addActionListener(evt -> {
      listener.save(chooseImage(false));
    });
    saveAllMenuItem.addActionListener(evt -> {
      listener.saveLayers(chooseDirectory(false));
    });
    rainbowMenuItem.addActionListener(evt -> {
      listener.setImage("rainbow",
          JOptionPane.showInputDialog("Enter the width and the height of the rainbow image: ")
              .split(" "));
    });
    checkerboardMenuItem.addActionListener(evt -> {
      listener.setImage("checkerboard",
          JOptionPane.showInputDialog(
              "Enter the number of horizontal squares, number of vertical squares, "
                  + "and the size of each square in pixels to generate a checkerboard.")
              .split(" "));
    });
    runBatchScriptMenuItem.addActionListener(evt -> {
      listener.script(chooseBatchScript().getAbsolutePath());
    });

    // Layers menu
    addMenuItem.addActionListener(evt -> {
      listener.add(JOptionPane.showInputDialog("Enter a name for the new layer."));
    });
    removeMenuItem.addActionListener(evt -> {
      listener.remove();
    });
    showMenuItem.addActionListener(evt -> {
      listener.visibility(true);
    });
    hideMenuItem.addActionListener(evt -> {
      listener.visibility(false);
    });
    currentListenerCreator = name -> (evt -> {
      listener.current(name);
    });
    moveMenuItem.addActionListener(evt -> {
      listener.move(JOptionPane.showInputDialog("Enter an new index for the current layer."));
    });

    blurImageItem.addActionListener(evt -> {
      listener.imageProcess(ImageOperationCreator.create(OperationType.BLUR));
    });
    sharpenImageItem.addActionListener(evt -> {
      listener.imageProcess(ImageOperationCreator.create(OperationType.SHARPEN));
    });
    sepiaImageItem.addActionListener(evt -> {
      listener.imageProcess(ImageOperationCreator.create(OperationType.SEPIA));
    });
    greyscaleImageItem.addActionListener(evt -> {
      listener.imageProcess(ImageOperationCreator.create(OperationType.GREYSCALE));
    });
    downscaleImageItem.addActionListener(ect -> {
      try {
        listener.imageProcessAll(new DownscaleOperation(Double.parseDouble(
            JOptionPane.showInputDialog("Enter a scale factor between 0 and 1"))));
      } catch (NumberFormatException e) {
        renderError("Invalid scale factor.");
      }
    });
  }

  /**
   * Updates the list of layers in the menu bar and side layers panel after a change is made to the
   * layers in the image processing program. Should only be called from the renderLayers method.
   */
  private void updateLayers(ImageProcessingModelState model) {
    currentMenu.removeAll();
    currentLayerMenuButtons = new ButtonGroup();
    layersPanel.removeAll();
    layerButtons = new ButtonGroup();

    for (int index = 0; index < model.numLayers(); index += 1) {
      String layerName = model.getLayerNameAt(index);

      // Menu
      JRadioButtonMenuItem button;
      if (layerName.equals(model.getCurrentName())) {
        button = new JRadioButtonMenuItem(layerName, true);
      } else {
        button = new JRadioButtonMenuItem(layerName, false);
      }
      if (currentListenerCreator != null) {
        button.addActionListener(currentListenerCreator.apply(layerName));
      }
      currentLayerMenuButtons.add(button);
      currentMenu.add(button);


      // Layers Panel
      JRadioButton layerButton;
      String text = index + 1 + ". " + layerName;
      if (model.isVisible(layerName)) {
        text += " (V)";
      } else {
        text += " ( )";
      }
      if (layerName.equals(model.getCurrentName())) {
        layerButton = new JRadioButton(text, true);
      } else {
        layerButton = new JRadioButton(text, false);
      }
      if (currentListenerCreator != null) {
        layerButton.addActionListener(currentListenerCreator.apply(layerName));
      }
      layersPanel.add(layerButton);
      layerButtons.add(layerButton);
    }

    layersPanel.revalidate();
    layersPanel.repaint();
  }

  /**
   * Updates the  image that is shown on screen after a change is made to the model. Should only be
   * called from the renderLayers method.
   */
  private void updateImage(ImageProcessingModelState model) {
    Image displayedImage = null;
    String displayedLayerName = null;
    for (int index = 0; index < model.numLayers(); index += 1) {
      String layerName = model.getLayerNameAt(index);
      Image layerImage = model.getImageIn(layerName);
      if (layerImage != null && model.isVisible(layerName)) {
        displayedImage = layerImage;
        displayedLayerName = layerName;
        break;
      }
    }
    if (displayedImage == null) {
      imageLabel.setIcon(null);
      imageCaption.setText("");
    } else {
      imageIcon = new ImageIcon(convertImage(displayedImage));
      imageLabel.setIcon(imageIcon);
      imageScrollPane.setPreferredSize(
          new Dimension(displayedImage.getWidth(), displayedImage.getHeight()));
      imageCaption.setText("Displaying: " + displayedLayerName);
    }
    revalidate();
  }

  /**
   * Displays a JFileChooser allowing the user to choose a supported image file from disk.
   *
   * @param open whether to show the open dialog. Shows the save dialog if false.
   * @return the chosen image file or null if no files is chosen.
   */
  private File chooseImage(boolean open) {
    final JFileChooser chooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPEG, PNG, and PPM (P3) Images", "jpeg", "png", "ppm");
    chooser.setFileFilter(filter);
    if (open) {
      chooser.showOpenDialog(mainSplitPlane);
    } else {
      chooser.showSaveDialog(mainSplitPlane);
    }
    return chooser.getSelectedFile();
  }

  /**
   * Displays a JFileChooser allowing the user to choose a directory from disk.
   *
   * @param open whether to show the open dialog. Shows the save dialog if false.
   * @return the chosen directory file or null if no files is chosen.
   */
  private File chooseDirectory(boolean open) {
    final JFileChooser chooser = new JFileChooser(".");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    if (open) {
      chooser.showOpenDialog(mainSplitPlane);
    } else {
      chooser.showSaveDialog(mainSplitPlane);
    }
    return chooser.getSelectedFile();
  }

  private File chooseBatchScript() {
    final JFileChooser chooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "TXT files only", "txt");
    chooser.setFileFilter(filter);
    chooser.showOpenDialog(mainSplitPlane);
    return chooser.getSelectedFile();
  }

  /**
   * Converts an Image into a BufferedImage with 8 bit color channels and no transparency.
   *
   * @param image the image to convert.
   * @return the converted BufferedImage.
   */
  private BufferedImage convertImage(Image image) {
    BufferedImage buffer = new BufferedImage(image.getWidth(),
        image.getHeight(), BufferedImage.TYPE_INT_BGR);
    for (int r = 0; r < image.getHeight(); r += 1) {
      for (int c = 0; c < image.getWidth(); c += 1) {
        Pixel pixel = image.getPixelAt(r, c);
        int rgb = pixel.getBlueValue()
            + (pixel.getGreenValue() << 8)
            + (pixel.getRedValue() << 16);
        buffer.setRGB(c, r, rgb);
      }
    }
    return buffer;
  }
}
