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
  }
}
