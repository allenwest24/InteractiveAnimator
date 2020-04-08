package cs3500.animator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cs3500.animator.controller.EditorViewController;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.*;
import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.AnimationModel;
import cs3500.excellence.IAnimation;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;
import cs3500.excellence.ShapeType;

/**
 * Run the animator based on command line inputs.
 */
public final class Excellence {
  /**
   * This is the main method, used to run the animator based on command line inputs.
   */
  public static void main(String[] args) {
    String in = null;
    String out = null;
    String view = "edit";
    String speed = "10";
    Appendable output = System.out;

    for (int ii = 0; ii < args.length; ii += 2) {
      switch (args[ii]) {
        case "-in":
          in = args[ii + 1];
          break;
        case "-out":
          out = args[ii + 1];
          break;
        case "-view":
          view = args[ii + 1];
          break;
        case "-speed":
          speed = args[ii + 1];
          break;
        default:
          break;
      }
    }
    /*if (in == null || view == null) {
      throw new IllegalArgumentException("Requirements not satisfied.");
    }*/
    int numSpeed = Integer.parseInt(speed);
    AnimationReader reader = new AnimationReader();
    AnimationBuilder<IAnimation<ShapeType>> builder = new AnimationModel.Builder();
    FileReader readableFile = null;
    try {
      readableFile = new FileReader("src/" + "smalldemo.txt");
    } catch (FileNotFoundException e) {
      System.out.println("Failed to find file.");
    }
    IAnimation<ShapeType> model = reader.parseFile(readableFile, builder);
    AnimationDelegate<Shape, Motion> delegateRef = (AnimationDelegate<Shape, Motion>) model;
    IView viewObject = Excellence.deriveCorrectView(view, delegateRef, numSpeed);
    EditorViewController evc;
    viewObject.makeVisible();
    if(view.equals("edit")) {
      evc = new EditorViewController(model, delegateRef, viewObject);
      evc.startUp();
    }
    else {
      viewObject.makeVisible();
    }
    if (out != null && (!view.equals("visual") && !view.equals("edit"))) {
      try {
        FileWriter writer = new FileWriter(out);
        writer.write(viewObject.stringOutputForFile());
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (!view.equals("visual") && !view.equals("edit")) {
      try {
        String outputString = viewObject.stringOutputForFile();
        output.append(outputString);
      } catch (IOException e) {
        e.printStackTrace();
      }
      catch (UnsupportedOperationException e) {
        e.printStackTrace();
      }
    }
  }

  private static IView deriveCorrectView(String viewType, AnimationDelegate<Shape, Motion> model,
                                         int speed) {
    IView view = null;
    switch (viewType) {
      case "text":
        view = new TextView(model);
        break;
      case "svg":
        view = new SVGView(model, speed);
        break;
      case "visual":
        view = new VisualView(model, speed);
        break;
      case "edit":
        view = new EditorView(model, speed);
        break;
    }
    return view;
  }
}
