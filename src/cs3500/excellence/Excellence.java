package cs3500.excellence;

import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;

public final class Excellence {
  public static void main(String[] args) {
    String in = null;
    String out = null;
    String view = null;
    String speed = "1";
    Appendable output = System.out;

    for (int ii = 0; ii < args.length; ii += 2) {
      switch (args[ii]) {
        case "-in":
          in = args[ii + 1];
          ii++;
          break;
        case "-out":
          out = args[ii + 1];
          ii++;
          break;
        case "-view":
          view = args[ii + 1];
          ii++;
          break;
        case "-speed":
          speed = args[ii + 1];
          ii++;
          break;
        default:
          break;
      }
    }

    if (in == null || view == null) {
      throw new IllegalArgumentException("Requirements not satisfied.");
    } else if (out != null) {

    }

    int numSpeed = Integer.parseInt(speed);

    AnimationReader reader = new AnimationReader();
    AnimationBuilder<IAnimation<ShapeType>> builder = new AnimationModel.Builder();



    //reader.parseFile()

    // Spin up a model (already spun up above)
    // Translate the fileName given into a Readable file
    // Spin up a new animation builder
    // Discover from the input what type of view these bastards want
    // Proceed to pass the readable file and the model (which will need a delete method, and
    // will need to implement the new AnimationBuilder interface) to the AnimationBuilder

    // Then, beautifully, the AnimationBuilder just goes ahead and automatically applies all of the
    // instructions provided in the file in question to the model via the methods we already
    // implemented.

    // Once this process is done, we are still in the main method (have been the whole time)
    // then we simply pass the model, which now has all the shapes and motions from the file,
    // courtesy of the AnimationBuilder, to whatever view choice the Main method specifies.

    // Tell the view to execute.
  }
}
