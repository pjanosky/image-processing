#Useme

##Text Interface

When running commands for the text-based controller, the user will be using the console to type the commands in. If this is the case, please refer to the command tmeplate below.

Running a script file: "script path-to-script-file". Change directories to the project directory to easily run scripts in the res folder.

Setting a layer to current: "current layer-name". The name of the layer has to exist within the displayed list of created layers.

Applying operations to images: as of now, there are four types of image operations supported by our implementation - blur, sharpen, sepia, and greyscale. The user needs to first set the layer to apply the operation on as the current layer, for the operations only apply on the current layer. An example flow of commands may be (assuming there are already layer1 and layer2 created): "current layer1" -> "sepia". 

Loading and saving all layers (i.e., importing and exporting multiple layer files as .png): the user needs to provide a path to a directory where the layer .png files are stored or to be saved in. The command template is "loadall full-path" or "saveall full-path". 

Hiding and showing layers: by calling "show" and "hide", it shows or hides the current layer. Similar to image operation commands, the user needs to set the layer they want to apply the visibility change to as the current layer before calling this command.

Creating an image programmatically: when the user wants to create an image (rainbow and checkerboard images are what our model currently supports) programmatically, the user needs to follow the given template. For example, the user may type "set rainbow 10 2" to create a rainbow image or "set checkerboard 2 4 1" for the checkerboard.

Update: Two new image operations are now supported: downsizing the image loaded on the layer and image mosaicing.

Downsizing the layers: when the user wants to downsize the images on all the currently existing layers, the user needs to follow the given command template: "downsize xfactor yfactor". The "xfactor" and "yfactor" represent the factor to downsize the width and height dimensions by. The factor needs to between 0 and 1. Remember: once this command is called, the images to be loaded should fit the modified dimensions. If the original dimensions were 200 by 150 and was downsized by 0.5 for both width and height, the future images that are loaded needs to fit the dimensions 100 by 75. The same applies for setting the layer with default rainbow or checkerboard images.

Image mosaicing: "mosaic number-of-seeds". The number of seeds is an integer. Make sure to load an image to mosaic before running this command. An example workflow for this command looks something of this: "add layer1" -> "load res/flowers.ppm ppm" -> "mosaic 8000" -> "q"

## Graphical Interface

When running the GUI, the user will be interacting with different menu options in the pop-up. Below the menu bar, the user is able to see the loaded image and its transformation. On the side to the image display is the list of layers. Whichever layer the user is currently on shall be selected in the radio button menu. The layers that are visible shall be indicated as (V) on the side, e.g. "1. Layer1 (V)".

There are three main tabs in the menu bar: "File", "Layer", "Image Processing". 

In "File" menu option, the user is able to select one of these options: loading an image to the current layer, loading all layers, saving the current layer as a desired image file, saving all layers as .png files, loading a preset image (rainbow or checkerboard), and running a batch script file. All except the option to load preset image will direct the user to the file finder to interact with. When loading an image, make sure to add a layer. For loading either types of preset image, the UI shows an input dialogue for the user to interact with. For Rainbow, the user is asked to input the width and height of the to-be-generated rainbow image, e.g., "200 150". For Checkerboard, the user is asked to input the number of horizontal squares, number of vertical squares, and the size of each square in pixels, e.g., "20 15 10". Each number will be separated by a space. 

In "Layer" menu option, the user is able to select one of these options: adding a layer, removing the current layer, hiding current layer, showing current layer, moving the current layer, and changing the current layer. When adding a layer, the input dialogue will ask the user for the name of the to-be-added layer. Make sure the layer name is one word, e.g., "Layer1" or "Background-Layer". As for changing the current layer, the user has the option to click on a different radio menu optioin OR click the desired layer listed in the dropdown. 

In "Image Processing" menu option, the user is able to select one of these options: blurring, sharpening, putting filters (sepia, greyscale), downscaling, mosaicing. All these operations are done on the current layer (for downscaling, the effect is applied to all the layers on the list). When applying these image operations, make sure the current layer has an image loaded. When downscaling, the input dialogue will ask the user for the two factors to downscaling the width and height of the image (two numbers between 0 and 1). The two numbers will be separated by a space. Remember, once the layers are downscaled, the same rule applies as the console version; the images that are to be loaded in the future are limited to the modified dimensions until further downscaling. When mosaicing the current layer, the input dialogue will ask the user for the number of seeds. The bigger the seed is, the more detailed the mosaiced image looks.

