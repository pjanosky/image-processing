package view;

import controller.GuiController;
import controller.commands.AddCommand;
import controller.commands.VisibilityCommand;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GUIView extends JFrame implements GUIImageProcessingView {

//  JButton importImageButton;
//  JButton exportLayerButton;

  JTextField addLayerNameField;

  JButton addButton;
  JButton showButton;


  public GUIView(String caption) {

    //find file to import/load
//    JPanel fileopenPanel = new JPanel();
//    fileopenPanel.setLayout(new FlowLayout());
//    dialogBoxesPanel.add(fileopenPanel);
//    importImageButton = new JButton("Load an image");
//    importImageButton.setActionCommand("Load image");
//    this.add(importImageButton);
//    fileOpenButton.addActionListener(this);
//    fileopenPanel.add(fileOpenButton);
//    fileOpenDisplay = new JLabel("File path will appear here");
//    fileopenPanel.add(fileOpenDisplay);

    //find a place to export/save
//    exportLayerButton = new JButton("Save the layer");
//    exportLayerButton.setActionCommand("Save layer");
//    this.add(exportLayerButton);

    showButton = new JButton("Show the layer");
    showButton.setActionCommand("Show layer");
    this.add(showButton);

    addLayerNameField = new JTextField(15);
    this.add(addLayerNameField);
  }

  @Override
  public void renderLayers() throws IOException {

  }

  @Override
  public void renderMessage(String message) throws IOException {

  }

  @Override
  public void addFeatures(GuiController features) {
    addButton.addActionListener(evt -> features.runCommand(
        new AddCommand(addLayerNameField.getName())
    ));
    showButton.addActionListener(evt -> features.runCommand(
        new VisibilityCommand(true)
    ));
    // add more action listeners for each command
    // can possible store 'features' object as a field if other methods need access to it
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
