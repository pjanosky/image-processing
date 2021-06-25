package view;

import controller.GuiProcessingController;
import controller.commands.AddCommand;
import controller.commands.CurrentCommand;
import controller.commands.LoadCommand;
import controller.commands.LoadLayersCommand;
import controller.commands.RemoveCommand;
import controller.commands.SaveCommand;
import controller.commands.SaveLayersCommand;
import controller.commands.VisibilityCommand;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Image;
import model.ImageProcessingModelState;
import model.Pixel;

public class GUIView extends JFrame implements GUIImageProcessingView {

  // Data:
  ImageProcessingModelState model;
  GuiProcessingController controller;


  // Panels:
  // main panel
  JPanel mainPanel;

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
  ButtonGroup layerMenuButtons;
  // TODO: Add menu item for move command

  // image processing menu
  // TODO: Add menu items for image processing commands


  public GUIView(ImageProcessingModelState model) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Model must not be null.");
    }
    this.model = model;

    setName("Image Processing");
    setSize(400, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setupMenu();
    setupPanel();
    updateLayers();
    updateImage();
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
    layerMenuButtons = new ButtonGroup();

    layerMenu.add(addMenuItem);
    layerMenu.add(removeMenuItem);
    layerMenu.add(hideMenuItem);
    layerMenu.add(showMenuItem);
    layerMenu.add(currentMenu);

    menuBar.add(layerMenu);
    // TODO: initialize menu item for the move command

    // Image Processing Menu (blur, sharpen, sepia, greyscale)
    // TODO: initialize image processing menu items

    // Update and set the menu bar
    this.setJMenuBar(menuBar);
  }

  /**
   * Configures the main panel for this view and adds it to the frame. Called from the constructor.
   */
  private void setupPanel() {
    // main panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

    // image panel
    imagePanel = new JPanel();
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imagePanel.add(imageLabel);
    imageCaption = new JLabel();
    imageCaption.setHorizontalAlignment(JLabel.CENTER);
    imagePanel.add(imageCaption);
    imageScrollPane = new JScrollPane(imagePanel);
    mainPanel.add(imageScrollPane);

    // layers panel
    layersPanel = new JPanel();
    layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
    layersPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    layersLabels = new ArrayList<>();
    layersScrollPane = new JScrollPane(layersPanel);
    mainPanel.add(layersScrollPane);

    // add to the view
    add(new JScrollPane(mainPanel));
  }

  public void renderLayers() throws IOException {
    //maybe here, we can control which image or layer is displayed? (yep)
  }

  @Override
  public void renderMessage(String message) throws IOException {
    JOptionPane.showMessageDialog(mainPanel, message, "", JOptionPane.PLAIN_MESSAGE);
  }

  public void renderError(String message) {
    JOptionPane.showMessageDialog(mainPanel, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setController(GuiProcessingController controller) {
    this.controller = controller;
    // TODO: Add checks for exceptions

    // File menu
    loadMenuItem.addActionListener(evt -> {
      File file = chooseImage(true);
      if (file != null) {
        controller.runCommand(new LoadCommand(file.getAbsolutePath(), parseExtension(file)));
      }
      updateImage();
    });
    loadAllMenuItem.addActionListener(evt -> {
      File file = chooseDirectory(true);
      if (file != null) {
        controller.runCommand(new LoadLayersCommand(file.getAbsolutePath()));
      }
      updateImage();
    });
    saveMenuItem.addActionListener(evt -> {
      File file = chooseImage(false);
      if (file != null) {
        controller.runCommand(new SaveCommand(file.getAbsolutePath(), parseExtension(file)));
      }
    });
    saveAllMenuItem.addActionListener(evt -> {
      File file = chooseDirectory(false);
      if (file != null) {
        controller.runCommand(new SaveLayersCommand(file.getAbsolutePath(),
            JOptionPane.showInputDialog("Chose a name for the new subdirectory"
                + " where layers will be saved.")));
      }
    });
    // TODO: add functionality for loading programmatic images menu items

    // Layers menu
    addMenuItem.addActionListener(evt -> {
      controller.runCommand(
          new AddCommand(JOptionPane.showInputDialog("Enter a name for the new layer.")));
      updateLayers();
    });
    removeMenuItem.addActionListener(evt -> {
      controller.runCommand(new RemoveCommand());
      updateLayers();
      updateImage();
    });
    showMenuItem.addActionListener(evt -> {
      controller.runCommand(new VisibilityCommand(true));
      updateImage();
    });
    hideMenuItem.addActionListener(evt -> {
      controller.runCommand(new VisibilityCommand(false));
      updateImage();
    });
    // TODO: add functionality for move command menu item

    // Image Processing menu
    // TODO: add functionality to image processing menu options

    // Update layers
    updateLayers();
  }

  /**
   * Updates the list of layers in the menu bar and side layers panel after a change is made to the
   * layers in the image processing program;
   */
  private void updateLayers() {
    // updated current layers menu bar item
    currentMenu.removeAll();
    layerMenuButtons = new ButtonGroup();
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
      if (controller != null) {
        button.addActionListener(evt -> {
          controller.runCommand(new CurrentCommand(layerName));
        });
      }
      layerMenuButtons.add(button);
      currentMenu.add(button);

      // Layers Panel
      String line = index + 1 + ". " + layerName;
      if (model.isVisible(layerName)) {
        line += " (V)";
      }else {
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
   * Updates the  image that is shown on screen after a change is made to the model.
   */
  private void updateImage() {
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
    imageCaption.setText("Displaying: " + displayedLayerName);
    mainPanel.revalidate();
  }

  /**
   * Parses the file extension for a file.
   *
   * @param file the file to get the extension of.
   * @return the file extension or an empty string if no extension can be parsed.
   */
  private String parseExtension(File file) {
    int extensionIndex = file.getName().lastIndexOf('.');
    if (extensionIndex < 0 || extensionIndex + 1 >= file.getName().length()) {
      return "";
    }
    return file.getName().substring(extensionIndex + 1);
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
      chooser.showOpenDialog(mainPanel);
    } else {
      chooser.showSaveDialog(mainPanel);
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
      chooser.showOpenDialog(mainPanel);
    } else {
      chooser.showSaveDialog(mainPanel);
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

/*
File choosers:

  private File loadedFilePathGetter() {
    File f = null;
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      f = fchooser.getSelectedFile();
    }
    return f;
  }

  private File saveFilePathGetter() {
    File f = null;
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      f = fchooser.getSelectedFile();
    }
    return f;
  }



//show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    //imagePanel.setMaximumSize(null);
    mainPanel.add(imagePanel);

    String[] images = {"Jellyfish.jpg", "Koala.jpg", "Penguins.jpg"};
    JLabel[] imageLabel = new JLabel[images.length];
    JScrollPane[] imageScrollPane = new JScrollPane[images.length];

    for (int i = 0; i < imageLabel.length; i++) {
      imageLabel[i] = new JLabel();
      imageScrollPane[i] = new JScrollPane(imageLabel[i]);
      imageLabel[i].setIcon(new ImageIcon(images[i]));
      imageScrollPane[i].setPreferredSize(new Dimension(100, 600));
      imagePanel.add(imageScrollPane[i]);
    }


    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(this);
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);
 */
