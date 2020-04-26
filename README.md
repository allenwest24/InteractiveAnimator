# Interactive Swing Animator
A java swing animator for parsing, interpreting, manipulating, and animating
user described animations from .txt files.

Utilizes [Inbetweening](https://en.wikipedia.org/wiki/Inbetweening) for computing visual properties over time.

Developed by Luke Andrews and Allen West.

### Supported Functionality:
- Looping animations.
- Dynamic addition/deletion of visuals.
- Basic controls: Play, Pause, Reset, etc.
- Dynamic display of animation over time via Swing JSlider scrubber.
- Live keyframe manipulation of visual size, speed, shape, color, position, etc.
- Dynamic organization controls (shape layering, layer swapping, etc.).
- Optional visual rotation support in .txt file interpretation.
- The output of described animations via SVG or .txt rendering.

### How to run the Animator jar with a demo file:
We've included a compiled .jar file (SwingAnimator.jar) in the src directory.
Try out the animator and its controls with the following terminal command!
- Make sure to navigate to the src folder before attempting to run the program.
-     landrews$ java -jar SwingAnimator.jar -in buildingsSUPERLayered.txt -speed 50 -view edit
- Experiment further with the various included .txt files, and make your own!

Export your custom animation files to SVG format with the following command:
-     landrews$ java -jar SwingAnimator.jar -in customAnimation.txt -speed 50 -view svg -out fileName.svg
Check out the various demo files in [this](https://github.com/lukeandrews239/exCELlence/tree/master/src) directory!
Credit for the demo files to [Dr. Clark Freifeld](https://www.khoury.northeastern.edu/people/clark-freifeld/).

### How to describe compatible animations:
Within a .txt file:

- To declare a new visual (rectangle or ellipse):
    - shape R rectangle
    - shape Z ellipse
 
- To customize visual properties (color, size, position, etc.) over time:
    -  describes the motions of shape R, between two moments of animation:
       - t == tick to begin the display of this visual
       - (x,y) == x & y positions
       - (w,h) == dimensions (width & height of visual)
       - (r,g,b) == color (with values between 0 and 255)
     -                 start                         end
                       --------------------------    ----------------------------
                       t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b
              motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0
              
- Define a visual's layer membership by prefacing its declaration with the following:
    - layer 1 (supports unlimited number of layers)
- Add rotations to individual visuals with the following specification:
    - (sT, eT) == beginning/ending tick for this rotation
    - (sR, eR) == beginning/ending radians for this rotation (0 - 359)
    -                  rotation
                       ------------
                       sT  eT  sR  eR 
              rotate R 01  40  00  200
              