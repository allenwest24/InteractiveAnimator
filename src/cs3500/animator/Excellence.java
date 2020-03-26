package cs3500.animator;


import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.excellence.AnimationModel;
import cs3500.excellence.IAnimation;
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
//
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

    System.out.println(model.stringRep());




  }
}


