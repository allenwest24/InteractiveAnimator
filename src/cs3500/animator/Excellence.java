package cs3500.animator;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;
import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.AnimationModel;
import cs3500.excellence.IAnimation;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;
import cs3500.excellence.ShapeType;

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

//    if (in == null || view == null) {
//      throw new IllegalArgumentException("Requirements not satisfied.");
//    } else if (out != null) {
////      try {
////        FileWriter writer = new FileWriter(out);
////        writer.write("TODOlukeOutput");
////        writer.close();
////      } catch (IOException e) {
////        e.printStackTrace();
////      }
//    }

    int numSpeed = Integer.parseInt(speed);

    AnimationReader reader = new AnimationReader();
    AnimationBuilder<IAnimation<ShapeType>> builder = new AnimationModel.Builder();

    FileReader readableFile = null;
    try {
      readableFile = new FileReader("src/" + "smalldemo.txt");
    } catch (FileNotFoundException e) {
      System.out.println("Failed");
    }

    IAnimation<ShapeType> model = reader.parseFile(readableFile, builder);
    AnimationDelegate<Shape, Motion> delegateRef = (AnimationDelegate<Shape, Motion>) model;
    IView viewObject = Excellence.deriveCorrectView(view);
    viewObject.acceptDelegate(delegateRef);
    System.out.println(delegateRef.getStringAnimation());
  }

  private static IView deriveCorrectView(String viewType) {
    IView view = null;
    switch (viewType) {
      case "text":
        view = new TextView();
        break;
      case "svg":
        view = new SVGView();
        break;
      case "visual":
        view = new VisualView();
        break;
    }
    return view;
  }
}


