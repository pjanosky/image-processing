package controller;

import controller.commands.ControllerCommand;
import java.io.File;
import model.ImageOperation;
import model.ImageOperationCreator;

public interface CommandListener {
  void add(String name);

  void remove();

  void current(String layerName);

  void move(String index);

  void imageProcess(ImageOperation operation);

  void imageProcessAll(ImageOperation operation);

  void load(File file);

  void loadLayers(File file);

  void save(File file);

  void saveLayers(File file);

  void setImage(String type, String... args);

  void visibility(boolean isVisible);

  void script(String filePath);
}

