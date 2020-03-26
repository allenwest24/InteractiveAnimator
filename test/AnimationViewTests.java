import org.junit.Test;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cs3500.animator.Excellence;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.AnimationModel;
import cs3500.excellence.IAnimation;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;
import cs3500.excellence.ShapeType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the IView interface, which contains the public methods needed for the view.
 */
public class AnimationViewTests {
  // Test constructors IView Constructors x3
  // Test stringOutputForFile() for visual.
  // Test stringOutputForFile() for text.
  // Test stringOutputForFile() for svg.
  // Test refresh() for visual.
  // Test refresh() for text.
  // Test refresh() for svg.
  // Test makeVisible() for visual.
  // Test makeVisible() for text.
  // Test makeVisible() for svg.
  // Test actionPerformed(ActionEvent e) for visual.
  AnimationBuilder<IAnimation<ShapeType>> builder;
  IAnimation<ShapeType> model;
  AnimationDelegate<Shape, Motion> delegateRef;
  IView view;

  void setUp(String file, boolean svgHuh, int speed) {
    FileReader readableFile = null;
    try {
      readableFile = new FileReader("src/" + file);
    } catch (FileNotFoundException e) {
      System.out.println("Failed to find file.");
    }
    builder = new AnimationModel.Builder();
    model = AnimationReader.parseFile(readableFile, builder);
    delegateRef = (AnimationDelegate<Shape, Motion>) model;
    // Create whichever type of view you are planning to test
    view = svgHuh ? new SVGView(delegateRef, speed) : new TextView(delegateRef);
  }

  @Test
  public void testTextDemoForSmallDemo() {
    // set this bool to false you get a text view (this.view is text, true it is svg)
    this.setUp("smalldemo.txt", false, 20);
    System.out.println(view.stringOutputForFile());
  }

  @Test
  public void testSvgDemoForSmallDemo() {
    // set this bool to false you get a text view (this.view is text, true it is svg)
    this.setUp("smalldemo.txt", true, 20);
    String expectedOutput = "<svg width=\"360\" height=\"360\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"x\" from=\"200\" to=\"200\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"y\" from=\"200\" to=\"200\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"width\" from=\"50\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"450ms\" attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"2000ms\" attributeName=\"x\" from=\"200\" to=\"300\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"2000ms\" attributeName=\"y\" from=\"200\" to=\"300\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"2000ms\" attributeName=\"width\" from=\"50\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"2000ms\" attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"500ms\" dur=\"2000ms\" attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"50ms\" attributeName=\"x\" from=\"300\" to=\"300\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"50ms\" attributeName=\"y\" from=\"300\" to=\"300\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"50ms\" attributeName=\"width\" from=\"50\" to=\"50\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"50ms\" attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"50ms\" attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2550ms\" dur=\"950ms\" attributeName=\"x\" from=\"300\" to=\"300\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2550ms\" dur=\"950ms\" attributeName=\"y\" from=\"300\" to=\"300\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2550ms\" dur=\"950ms\" attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2550ms\" dur=\"950ms\" attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2550ms\" dur=\"950ms\" attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" attributeName=\"x\" from=\"300\" to=\"200\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" attributeName=\"y\" from=\"300\" to=\"200\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" attributeName=\"width\" from=\"25\" to=\"25\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "<ellipse id=\"C\" cx=\"440\" cy=\"70\" rx=\"120\" ry=\"60\" fill=\"rgb(0,0,255)\" visibility=\"visible\" >\n" +
            "<animate attributeType=\"xml\" begin=\"300ms\" dur=\"700ms\" attributeName=\"cx\" from=\"440\" to=\"440\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"300ms\" dur=\"700ms\" attributeName=\"cy\" from=\"70\" to=\"70\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"300ms\" dur=\"700ms\" attributeName=\"rx\" from=\"120\" to=\"120\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"300ms\" dur=\"700ms\" attributeName=\"ry\" from=\"60\" to=\"60\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"300ms\" dur=\"700ms\" attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,0,255)\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1500ms\" attributeName=\"cx\" from=\"440\" to=\"440\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1500ms\" attributeName=\"cy\" from=\"70\" to=\"250\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1500ms\" attributeName=\"rx\" from=\"120\" to=\"120\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1500ms\" attributeName=\"ry\" from=\"60\" to=\"60\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" dur=\"1500ms\" attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,0,255)\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"1000ms\" attributeName=\"cx\" from=\"440\" to=\"440\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"1000ms\" attributeName=\"cy\" from=\"250\" to=\"370\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"1000ms\" attributeName=\"rx\" from=\"120\" to=\"120\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"1000ms\" attributeName=\"ry\" from=\"60\" to=\"60\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"2500ms\" dur=\"1000ms\" attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"500ms\" attributeName=\"cx\" from=\"440\" to=\"440\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"500ms\" attributeName=\"cy\" from=\"370\" to=\"370\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"500ms\" attributeName=\"rx\" from=\"120\" to=\"120\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"500ms\" attributeName=\"ry\" from=\"60\" to=\"60\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"500ms\" attributeName=\"fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"1000ms\" attributeName=\"cx\" from=\"440\" to=\"440\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"1000ms\" attributeName=\"cy\" from=\"370\" to=\"370\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"1000ms\" attributeName=\"rx\" from=\"120\" to=\"120\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"1000ms\" attributeName=\"ry\" from=\"60\" to=\"60\" fill=\"remove\" />\n" +
            "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"1000ms\" attributeName=\"fill\" from=\"rgb(0,255,0)\" to=\"rgb(0,255,0)\" fill=\"remove\" />\n" +
            "</ellipse>\n" +
            "</svg>";
    assertTrue(this.view.stringOutputForFile().equals(expectedOutput));
  }


}
