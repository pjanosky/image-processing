# Image Processing

Image Processing is a desktop image editor for processing multi-layered images.

## Usage
A compiled version of ImageProcessing can be found in out/ImageProcessing.jar. It can be run with Java 11. By default, the program launches with the interactive GUI, but a command based interface can also be used by adding the -text flag. Additionally, a script of image processing commands can be run by adding the -scrip flag and specifying the path to the script. The various image processing commands is described below:

 * save \<file path\> \<format\> *- saves the current layer to disk*
 * load \<file path\> \<format\> *- loads an image from disk into the current layer*
 * show *- show the current layer*
 * hide *- hide the current layer*
 * saveall \<directory path\> \<name of save directory\> *- save all layers to a directory*
 * loadall \<path to save directory\> *- load all saved layers*
 * add \<layer name\> *- add a new layer*
 * remove *- remove the current layer*
 * sharpen *- apply the sharpen filter to the current layer*
 * blur *- apply the blur filter to the current layer*
 * sepia *- apply the sepia color filter to the current layer*
 * greyscale *- apply the greyscale color filter to the current layer*
 * script \<path the scrip\> *- run a script of image processing commands*
 * current \<layer name\> *- set a layer as the current layer*
 * move \<layer index\> *- move the current layer*
 * set \<args...\> *- create an image programmatically*
 * downscale \<horizontal scale\> \<vertical scale\> *- downscale the current image*
 * mosaic \<number of seeds\> *- create a mosaic design from the current image*

## Technologies
This project was developed entirely using Java. The code for the image editing commands and effects were written ground-up, without relying on external libraries. The GUI was implemented using the Java Swing API for a compatibility with a variety of technologies.

## Purpose
This project was created to practice and explore object-oriented design concepts leaned in CS 3500 at Northeastern University. Flexibility, extensibility and modularity were factored into the program architecture at every step. To accomplish this, Image Processing makes heavy use of abstraction, encapsulation, and the model-view-controller (MVC) framework. Additionally, over 300 unit and integration tests were written to ensure code correctness.