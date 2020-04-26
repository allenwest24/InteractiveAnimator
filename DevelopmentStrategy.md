# Interactive Animator
A java swing animator, utilizing the delegate, MVC, decorator, and builder patterns.
Developed by Luke Andrews and Allen West.

## Phase 1:  Model Implementation:
The key data structures of our design begin with the two interfaces
(first of which is tagged below). The first, IAnimation, is a loosely coupled
interface that defines the mandatory functionality of an Animation (namely
the ability to declare shapes and motions). The second, AnimationDelegate,
is an effectively read-only interface that provides a view of this model
with data that will need to be displayed. Any objects passed via these methods
must be COPY-SAFE (i.e. objects that the model does not hold references to
only; copies) as noted in the constructor. This is in anticipation of a view
being developed in the future. 

The major data structures consist of the model (AnimationModel, the
implementation of the IAnimation interface), a custom Color object that
has a comparison method (we elected to go with a custom Color object so
that we could enforce the int range restraints (0-255) on all values
passed in), and a Motion object and a Shape object (along with two other
objects that I will define below).

The AnimationModel declares shapes, after validating the provided
input is legitimate. If legitimate, we create a new shape object with
the associated ShapeType (an enum that represents types of shapes, one
of the function's parameters) and then proceed to store this object in
the hashmap in AnimationModel, where the unique string name is the key.
The Shape object has an immutable arrayList of Motions. When a new motion
is proposed by the client of this model via the public interface method,
we proceed according to the following process.

First we verify the motion is valid, and that we are storing a valid
shape object in our Model's hashmap associated with the name provided
to the motion creation function (method tagged in second tag slot). If
these conditions are met, we proceed to translate the new command into a
motion object (which, with the optionallyDeriveMotion method on line 113
of AnimationModel, accomplishes). Then, we attempt to add this motion to
the shape that we looked up via the name provided to this method. This
occurs in the conditionallyAddMotionToShape method on the Shape object,
tagged in the third slot). Having already established that the motion
is valid in nature, we now proceed to grab the last motion associated
with this shape (treating the arraylist motion property on Shape as a stack,
although we never truly remove any motions from it). Then, with the static
computeNextMotion method on the Motion object, we attempt to create a new
motion consisting of the user's new input for the shape object. Here, we
ensure that all implicit dependencies are explicitly enforced, for instance,
ensuring that the startX and startY of the newly proposed motion are
equivalent to the endX and endY values of the last motion associated with
this shape, etc. If the motion, having already been established as a valid
one, is indeed compatible with this shape, the static factory method
returns a new motion to the conditionallyAddMotion to shape method, which
then accepts that motion. This process allows us to ensure all motions have
all relevant data (for instance, a command that only says to change
coordinate locations doesn't require any input for color or height/width.
The computeNextMotion method takes the newly provided coordinates (provided
that they are compatible with the last coordinates the shape stores), and
fills in the rest of the new motion object with the last up-to-date values
for those properties on this shape, derived from the last stored motion we
are using to validate the newly proposed one. If this process is successful,
conditionallyAddMotion to shape returns true, allowing the success to
trickle back up to the model. In that case, the model proceeds to store a
copy of this motion as a UserInteraction abstract class in the arraylist
allMoves property on the model. Both Shape and Motion inherit from this
abstract class, which has only one mandatory abstract method on it - userMove.
That way, when we need to formulate output for the AnimationDelegate methods
implemented on the model, we can easily iterate over the entire list of
commands chronologically, without discriminating or even taking note of
the differences between shapes and motions (we just call the userMove
method on each object on the array, and via dynamic dispatch the correct
string output is formulated, whether that be a shape or a motion.).

## Phase 2: Swing view, first pass at JAR, SVG + Text compiler for output:
For this phase, we made several changes to our code from the first phase.
Firstly, we created a new static Builder class within our AnimationModel
implementation. This class implements the AnimationBuilder interface,
parameterized to the IAnimation interface that AnimationModel itself
implements. This allows the builder implementation to statically store
a copy of an animation model, and appropriately translate the client input
from the various required methods in AnimationBuilder into input that our
model is expecting. To achieve this, we only had to make one extremely small
change, namely, creating a method that derives a ShapeType enumeration
case instance from string input representing possible shape types.

The only other small changes that we made were as follows. We added a method
on the AnimationDelegate protocol (and implementation of it in AnimationModel)
that serves to provide view clients with the bounds object the model was informed
of. We also added a method to the IAnimation interface that allowed the builder
class to inform the model of Bounds that it had received from it's client.

Furthermore, Motion and Shape have always been immutable value types that
we provided to view clients through the AnimationDelegate interface. However,
the access level of their READONLY immutable fields was protected, as we
had initially assumed that further additions to the project would be in the
same package. We safely adjusted the fields of these objects to public access,
as they are completely immutable value objects that were intended to be
viewable by the view client (demonstrated by the fact that they can be
retrieved by delegates via the AnimationDelegate interface).

Otherwise, our views all are final, and not subclassable. They implement the
IView interface, which allows us to treat them the same way in the new
Excellence class (also not subclassable). The excellence class serves to 
provide a main method, such that clients can create various types of
animations by specifying a file, speed, and type of view. We support the
creation of txt and svg files that represent such views, as well as a Java
Swing Animator implementation. All views are instantiated with an instance
of the Model paramertarized by it's read-only delegate protocol, such that
they can access the immutable motion and shape objects that they need to
render animations.

## Phase 3: View Controller, robust control additions:
For this phase, we added a new directory that contains an editor view controller
in addition to a view controller interface and a view controller delegate interface.
The view controller interface has only one method to be called from the main method
which turns over program control to the view control. The view controller
is in charge of starting the program and managing interaction between the 
model and the views. This view controller spins up an editor view which
is the new addition to our view package. This view uses the "decorator \
pattern" in addition to the delegate pattern as it creates a visual view and 
a drop down menu panel independently. This object manages the user 
interactions with our new drop down panel translating that input into
input for the mutable copy of the model that it stores. It then also manages
the visual display of the results of these changes by instructing its sub
views to update when needed. The changes that we made to existing code 
include the implementation of three methods on the IAnimation interface
in our model. These methods allow for the deletion of shapes as well as the 
the deletion and addition of Key Frames. Luckily, our prior management
strategy of motions allowed us to accomplish this addition relatively easily.

We also added various controls to the Swing GUI for the animator, including
looping, keyframe manipulation, state management of objects being animated,
a scrubber, rotation for shapes, and layering of animation components.


