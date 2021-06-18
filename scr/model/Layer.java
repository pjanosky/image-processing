package model;

public interface Layer {

  void setName(String name);

  void setImage(Image image);

  void show(boolean isVisible);

  void apply(ImageOperation operation);

  String getName();

  Image getImage();
  
  boolean isVisible();
}

/*
name
get image
set image
apply operation


class Blend implement ImageOperation {
  Blend(Image other) {
  }

  Image apply(Image image) {
    (blend 'image' and 'other')
  }
}


A blend method in the model, or a operation in the controller?:
  1. get image from layer 1
  2. create new Blend with that image
  3. apply that Blend operation to layer 2
  4. delete layer 1
*/