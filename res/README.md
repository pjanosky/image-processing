The main interface for our model is the ImageProcessingModel interface which has all the operations that a client would need to load, alter, and save images. This interface extends the ImageProcessingModelState interface which specifies the read only operations that a model should support. We made this distinction between the two interfaces to support future designs where we need to limit the operations that a client can perform on a model. For example, a view should not be able to modify the model's state. The ImageProcessingModelImpl class is our implementation of these interfaces.

Images in our program are represented by the Image interface which lists a handful of operations that provide information about specific parts of the image (pixels, color channels, etc.). We implemented this interface with an abstract class called AbstractImage. This class implements almost all of the functionality required by the interface. We made this class abstract to add the flexibility to support multiple bit numbers for colors. For example, the concrete class, Image24Bit, supports 8-bit color values.

AbstractImage stores image data as a collection of Pixels. The Pixel interface represents the color of one pixel in an image. Its implementation, Rgb pixel, stores this data as red, green, and blue color values. Both Pixels and Images are immutable. We made this decision to prevent unintended modification of our model's state.

Image edits are represented by the ImageOperation interface. In essence, an operation on an image just something that modifies an image to produce a new image, so the interface defines a single apply method. The two types of image operations are represented by two classes that implement the ImageOperationInterface: FilterOperation and ColorTransformation. These classes are parameterized by the kernel or linear transformation matrix that defines a certain filter/transformation. Different kinds of filters can be created by constructing a FilterOperation with a different kernel, and likewise for color transformations.

For the flexibility to support importing and export different image file formats, we designed the ImageImportExporter interface which is implemented by the PpmImportExporter class. This class implements the ability to import and export PPM format images.

Lastly, we defined a factory class, ImageOperationCreator, to allow us to easily create some default ImageTransformation objects such as blur, sharpen, greyscale, and sepia as required by the assignment.


Modifications to the model:
 - Changed ImageProcessingModelState and ImageProcessingModel interfaces to work with Layers instead of individual Images. The model implementation and associated interfaces were  completely rewritten. Updated ImageProcessingModelImpl to match.
 - Moved import and export methods from ImageProcessingModel to the controller as they deal with IO.
 - The model supports importing and exporting of two other image types: jpeg and png. The original import-exporter is abstracted to minimize the amount of changes to make if there is a need to support new file format in the future.

We added two new interfaces: ImageProcessingController and ImageProcessingView. The two newly added interfaces contain methods that allows the user to interact and see respectively. Due to the changes we made to the model, the controller is able to import and export different image types. The commands the controller supports will be further elaborated in the USEME file.

As for the view, it renders the state (the index, name, visibility, and whether it is the current layer or not) of layers stored in the model and if needed, renders messages. 


Sample Image Citations:
“Angiosperms.” British Broadcasting Corporation, 14 Jan. 2018,
  ichef.bbci.co.uk/news/976/cpsprodpb/9110/production/_99563173_gettyimages-479233691.jpg.
“Giant Panda.” World Wildlife Fund, 2021,
  c402277.ssl.cf1.rackcdn.com/photos/18315/images/hero_full/Medium_WW230176.jpg?1576168323.