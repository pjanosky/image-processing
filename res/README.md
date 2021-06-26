# Readme

## Program Overview

The main interface for our model is the ImageProcessingModel interface which has all the operations that a client would need to load, alter, and save images. This interface extends the ImageProcessingModelState interface which specifies the read only operations that a model should support. 

Images in our program are represented by the Image interface which lists a handful of operations that provide information about specific parts of the image (pixels, color channels, etc.). We implemented this interface with an abstract class called AbstractImage. This class implements almost all of the functionality required by the interface. We made this class abstract to add the flexibility to support multiple bit numbers for colors. For example, the concrete class, Image24Bit, supports 8-bit color values.

AbstractImage stores image data as a collection of Pixels. The Pixel interface represents the color of one pixel in an image. Its implementation, Rgb pixel, stores this data as red, green, and blue color values. Both Pixels and Images are immutable. We made this decision to prevent unintended modification of our model's state.

Image edits are represented by the ImageOperation interface. In essence, an operation on an image just something that modifies an image to produce a new image, so the interface defines a single apply method. The program currently supports 4 kinds of image operation in both interactive and text-based interface modes: FilterOperation, ColorTransformation, DownscaleOperation, and MosaicOperation. ColorTransformation and FilterOperation are parameterized by the kernel or linear transformation matrix that defines a certain filter/transformation. Different kinds of filters can be created by constructing a FilterOperation with a different kernel, and likewise for color transformations.

For the flexibility to support importing and export different image file formats, we designed the ImageImportExporter interface which has three implementations for ppm, png, and jpeg images.

Lastly, we defined a factory class, ImageOperationCreator, to allow us to easily create some default ImageTransformation objects such as blur, sharpen, greyscale, and sepia.

Controllers are represented by the ImageProcessingController interface with a single run method to start the program. Two implementations of interface exist: TextController, and GuiController.

Views are represented by the ImageProcessingView interface. Additionally, the GuiImageProcessingView interface extends ImageProcessing view to add new functionality that is required for a graphical interface. For example, the view can set a listener that is notified whenever the user performs an action.

## List of Features
 - Downscaling extra credit is working both in the graphical interface and the text based interface.
   - Two sample images are provided in the res/ folder which are downscaled version of the original image panda.png. panda_downscale1.png has been downscaled by 0.75 along both axis, and panda_downscale2.png has been downscaled by 0.4 in the horrizontal direction and 0.8 in the vertical direction.
 - Mosaic extra credit is working both in the graphical interface and the text based interface.
   - Three sample images are provided in the res/ foler which are mosaiced versions of the original image panda.png. panda_mosaic_50seed has been mosaiced with 50 seeds, panda_mosaic_200seed.png has been mosaiced with 200 seeeds, and panda_mosiac_1000seed has been mosaiced with 1000 seeds.
 - All other parts of the program are working per the assignment specifications.


## Design Changes (from Assignment 6)

 - Added the GuiImageProcessingView and CommandListener interfaces. Added the GuiView and GuiController classes.
 - Changed the runCommand method in ImageProcessingController interface to take in the view as a parameter. This allows the command objects to render messages to the view to notify the user that certain actions have been performed. 
 - Changed the rainbow method in the ImageExamples class to take in a height for the image.
 - Make the view, modelView, and ScriptCommand fields and inner classes in the TextController class protected instead of private. This allows the GuiController to reuse code in the text controller to run script files, since scripts files contain textual commands.
 - Slightly modified the ImageProcessingView interface to more of a "push" design. The renderLayers method now takes in a read-only copy of the view instead of implementations storing the model as a field. This more closely follows MVC and facilitates better design.
 - Added new controller command for applying an image operation to all images in the model.
 - Added new mosaic and downscal commands to the map of commands in the text view.




 ## Image Citations:
“Angiosperms.” British Broadcasting Corporation, 14 Jan. 2018,
  ichef.bbci.co.uk/news/976/cpsprodpb/9110/production/_99563173_gettyimages-479233691.jpg.
“Giant Panda.” World Wildlife Fund, 2021,
  c402277.ssl.cf1.rackcdn.com/photos/18315/images/hero_full/Medium_WW230176.jpg?1576168323.