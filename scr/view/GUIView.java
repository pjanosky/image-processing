package view;

import controller.CommandListener;
import controller.commands.CurrentCommand;
import controller.commands.ImageProcessCommand;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
  List<JLabel> layersLabels;


  // Menus:
  // file menu
  JMenu fileMenu;
  JMenuItem loadMenuItem;
  JMenuItem loadAllMenuItem;
  JMenuItem saveMenuItem;
  JMenuItem saveAllMenuItem;
  // TODO: Add menu item for loading programmatic images

  // layers menu
  JMenu layerMenu;
  JMenuItem addMenuItem;
  JMenuItem removeMenuItem;
  JMenuItem showMenuItem;
  JMenuItem hideMenuItem;
  JMenu currentMenu;
  ButtonGroup currentLayerMenuButtons;
  Function<String, ActionListener> currentListenerCreator;

  // TODO: Add menu item for move command

  // image processing menu
  JMenu imageProcessMenu;
  JMenuItem blurImageItem;
  JMenuItem sharpenImageItem;
  JMenuItem greyscaleImageItem;
  JMenuItem sepiaImageItem;
  // TODO: Add menu items for image processing commands


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

    fileMenu.add(loadMenuItem);
    fileMenu.add(loadAllMenuItem);
    fileMenu.add(saveMenuItem);
    fileMenu.add(saveAllMenuItem);

    menuBar.add(fileMenu);
    // TODO: initialize menu item for loading programmatic images

    // Layers Menu (add, remove, hide/show, current)
    layerMenu = new JMenu("Layer");

    addMenuItem = new JMenuItem("Add Layer");
    removeMenuItem = new JMenuItem("Remove Current Layer");
    showMenuItem = new JMenuItem("Show Current Layer");
    hideMenuItem = new JMenuItem("Hide Current Layer");
    currentMenu = new JMenu("Current Layer");
    currentLayerMenuButtons = new ButtonGroup();

    layerMenu.add(addMenuItem);
    layerMenu.add(removeMenuItem);
    layerMenu.add(hideMenuItem);
    layerMenu.add(showMenuItem);
    layerMenu.add(currentMenu);

    menuBar.add(layerMenu);
    // TODO: initialize menu item for the move command

    // Image Processing Menu (blur, sharpen, sepia, greyscale)
    imageProcessMenu = new JMenu("Image Processing");

    blurImageItem = new JMenuItem("Blur Current Layer");
    sharpenImageItem = new JMenuItem("Sharpen Current Layer");
    sepiaImageItem = new JMenuItem("Sepia Current Layer");
    greyscaleImageItem = new JMenuItem("Greyscale Current Layer");

    imageProcessMenu.add(blurImageItem);
    imageProcessMenu.add(sharpenImageItem);
    imageProcessMenu.add(sepiaImageItem);
    imageProcessMenu.add(greyscaleImageItem);

    menuBar.add(imageProcessMenu);
    // TODO: initialize image processing menu items

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
    layersLabels = new ArrayList<>();
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
      listener.saveLayers(chooseDirectory(false),
          JOptionPane.showInputDialog("Chose a name for the new subdirectory"
              + " where layers will be saved."));
    });
    // TODO: add functionality for loading programmatic images menu items

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
    currentListenerCreator = name -> (evt -> new CurrentCommand(name));
    // TODO: add functionality for move command menu item

    // Image Processing menu
    blurImageItem.addActionListener(evt -> {
      listener.imageProcess(OperationType.BLUR);
    });
    sharpenImageItem.addActionListener(evt -> {
      listener.imageProcess(OperationType.SHARPEN);
    });
    sepiaImageItem.addActionListener(evt -> {
      listener.imageProcess(OperationType.SEPIA);
    });
    greyscaleImageItem.addActionListener(evt -> {
      listener.imageProcess(OperationType.GREYSCALE);
    });
    // TODO: add functionality to image processing menu options
  }

  /**
   * Updates the list of layers in the menu bar and side layers panel after a change is made to the
   * layers in the image processing program. Should only be called from the renderLayers method.
   */
  private void updateLayers(ImageProcessingModelState model) {
    // updated current layers menu bar item
    currentMenu.removeAll();
    currentLayerMenuButtons = new ButtonGroup();
    layersPanel.removeAll();
    layersLabels.clear();
    for (int index = 0; index < model.numLayers(); index += 1) {
      String layerName = model.getLayerNameAt(index);
      JRadioButtonMenuItem button;

      if (layerName.equals(model.getCurrentName())) {
        button = new JRadioButtonMenuItem(layerName, true);
      } else {
        button = new JRadioButtonMenuItem(layerName, false);
      }

      // Menu
      if (currentListenerCreator != null) {
        button.addActionListener(currentListenerCreator.apply(layerName));
      }
      currentLayerMenuButtons.add(button);
      currentMenu.add(button);

      // Layers Panel
      String line = index + 1 + ". " + layerName;
      if (model.isVisible(layerName)) {
        line += " (V)";
      } else {
        line += "( )";
      }
      if (layerName.equals(model.getCurrentName())) {
        line += " (current)";
      }
      layersLabels.add(new JLabel(line));
    }
    for (JLabel label : layersLabels) {
      layersPanel.add(label);
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
      return;
    }
    imageIcon = new ImageIcon(convertImage(displayedImage));
    imageLabel.setIcon(imageIcon);
    imageScrollPane.setPreferredSize(
        new Dimension(displayedImage.getWidth(), displayedImage.getHeight()));
    imageCaption.setText("Displaying: " + displayedLayerName);
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
    FileFilter filter = chooser.getFileFilter();
    chooser.setFileFilter(new DirectoryFileFilter());
    if (open) {
      chooser.showOpenDialog(mainSplitPlane);
    } else {
      chooser.showSaveDialog(mainSplitPlane);
    }
    return chooser.getSelectedFile();
  }

  /**
   * A file filter that only accepts directories.
   */
  private static class DirectoryFileFilter extends FileFilter {

    @Override
    public boolean accept(File pathname) {
      return pathname.isDirectory();
    }

    @Override
    public String getDescription() {
      return "Directories";
    }
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
