package cs3500.excellence;

import cs3500.excellence.controller.AnimationController;
import cs3500.excellence.controller.Controller;
import cs3500.excellence.view.SVGView;
import cs3500.excellence.view.TextView;
import cs3500.excellence.view.View;
import cs3500.excellence.view.VisualView;

public final class Excellence {
  public static void main(String[] args) {
    String in = null;
    String out = null;
    String view = null;
    int speed = 1;
    // args.length - 1 because we never want to switch on the last argument.
    for (int ii = 0; ii < args.length - 1; ii++) {
      switch (args[ii]) {
        case ("-in"):
          in = args[ii+1];
          ii++;
          break;
        case ("-out"):
          out = args[ii+1];
          ii++;
          break;
        case ("-view"):
          view = args[ii+1];
          ii++;
          break;
        case ("-speed"):
          speed = Integer.parseInt(args[ii+1]);
          ii++;
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + args[ii]);
      }
    }
    if (in == null || view == null) {
      throw new IllegalArgumentException("Must provide and input file and view.");
    }
    AnimationModel model = new AnimationModel();
    View viewType;
    switch (view) {
      case "svg":
        viewType = new SVGView(model);
        break;
      case "visual":
        viewType = new VisualView(model);
        break;
      case "text":
        viewType = new TextView(model);
        break;
      default:
        throw new IllegalArgumentException("Should not be able to throw this error.");
    }
    AnimationController controller = new Controller(viewType, speed, in, out);
    controller.animate(model);

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
