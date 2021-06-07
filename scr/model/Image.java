package model;

public interface Image {
  int getWidth();

  int getHeight();

  int getRedValueAt(int x, int y);

  int getGreenValueAt(int x, int y);

  int getBlueValueAt(int x, int y);

  Pixel getPixelAt(int x, int y);
}
