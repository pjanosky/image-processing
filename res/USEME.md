Running a script file: "script path-to-script-file". Change directories to the project directory to easily run scripts in the res folder.

Setting a layer to current: "current layer-name". The name of the layer has to exist within the displayed list of created layers.

Applying operations to images: as of now, there are four types of image operations supported by our implementation - blur, sharpen, sepia, and greyscale. The user needs to first set the layer to apply the operation on as the current layer, for the operations only apply on the current layer. An example flow of commands may be (assuming there are already layer1 and layer2 created): "current layer1" -> "sepia". 

Loading and saving all layers (i.e., importing and exporting multiple layer files as .png): the user needs to provide a path to a directory where the layer .png files are stored or to be saved in. The command template is "loadall full-path" or "saveall full-path". 

Hiding and showing layers: by calling "show" and "hide", it shows or hides the current layer. Similar to image operation commands, the user needs to set the layer they want to apply the visibility change to as the current layer before calling this command.

Creating an image programmatically: when the user wants to create an image (rainbow and checkerboard images are what our model currently supports) programmatically, the user needs to follow the given template. For example, the user may type "set rainbow 10 2" to create a rainbow image or "set checkerboard 2 4 1" for the checkerboard.