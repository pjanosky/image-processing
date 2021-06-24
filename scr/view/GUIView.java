package view;

import controller.GuiProcessingController;
import controller.commands.AddCommand;
import controller.commands.LoadCommand;
import controller.commands.SaveCommand;
import controller.commands.VisibilityCommand;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GUIView extends JFrame implements GUIImageProcessingView {

  //main panel
  JPanel main;
  JScrollPane mainScrollPane;

  //menu panel
  JPanel menu;

  //text field to add the layer name
  JTextField addLayerNameField;

  //buttons to be displayed
  JButton addButton;
  JButton showButton;
  JButton hideButton;
  JButton importImageButton;
  JButton exportLayerButton;

  //image panel; one can scroll up, down, left, right
  JPanel imageDisplay;


  public GUIView(String caption) {
    super(caption);
    setSize(400, 400);
    setLayout(new FlowLayout());

    main = new JPanel();
    main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(main);
    add(mainScrollPane);

    menu = new JPanel();
    menu.setBorder(BorderFactory.createTitledBorder("menu"));
    menu.setLayout(new BoxLayout(menu, BoxLayout.PAGE_AXIS));
    main.add(menu);

    addButton = new JButton("Add a layer");
    addButton.setActionCommand("Add layer");
    menu.add(addButton);

    showButton = new JButton("Show the layer");
    showButton.setActionCommand("Show layer");
    menu.add(showButton);

    hideButton = new JButton("Hide the layer");
    hideButton.setActionCommand("Hide layer");
    menu.add(hideButton);

    importImageButton = new JButton("Load an image");
    importImageButton.setActionCommand("Load image");
    menu.add(importImageButton);

    exportLayerButton = new JButton("Save the layer");
    exportLayerButton.setActionCommand("Save layer");
    menu.add(exportLayerButton);

    //text field
    addLayerNameField = new JTextField(15);
    menu.add(addLayerNameField);

    //image display
    imageDisplay = new JPanel();
    //a border around the panel with a caption
    imageDisplay.setBorder(BorderFactory.createTitledBorder("Layer display"));
    imageDisplay.setLayout(new GridLayout(1, 0, 10, 10));
    //imagePanel.setMaximumSize(null);
    main.add(imageDisplay);
  }

  public void renderLayers() throws IOException {
    //maybe here, we can control which image or layer is displayed?
  }

  @Override
  public void renderMessage(String message) throws IOException {
    //display activity message via here; mutating the JLabel object?
  }

  @Override
  public void addFeatures(GuiProcessingController features) {
    addButton.addActionListener(evt -> features.runCommand(
        new AddCommand(addLayerNameField.getName())
    ));
    showButton.addActionListener(evt -> features.runCommand(
        new VisibilityCommand(true)
    ));
    hideButton.addActionListener(evt -> features.runCommand(
        new VisibilityCommand(false)
    ));
//    importImageButton.addActionListener(evt -> features.runCommand(
//        new LoadCommand(loadedFilePathGetter().getAbsolutePath())
//    ));
    exportLayerButton.addActionListener(evt -> features.runCommand(
        new SaveCommand(saveFilePathGetter().getAbsolutePath(), "png") //<-- is this correct?
    ));

    // add more action listeners for each command
    // can possible store 'features' object as a field if other methods need access to it
  }

  @Override
  public void clearInputString() {
    addLayerNameField.setText("");
  }

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

}

/*

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
