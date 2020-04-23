package cs3500.animator.view;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.animator.controller.VCDelegate;
import cs3500.excellence.*;

/**
 * Concrete implementation of a svg "IView" with the mandatory functionality demanded by that
 * interface.
 */
public final class SVGView implements IView {

  private AnimationDelegate<Shape, Motion> delegate;
  private int speed;

  /**
   * Public constructor for this object.
   *
   * @param delegate model with "read only" access to its states.
   */
  public SVGView(AnimationDelegate<Shape, Motion> delegate, int speed) {
    this.delegate = delegate;
    this.speed = speed;
  }

  /**
   * Get the String version of the output for the specific type of view.
   *
   * @return the String representing the view or an empty String if the view is visual.
   * @throws UnsupportedOperationException for views that do not have a String output.
   */
  @Override
  public String stringOutputForFile() {
    StringBuilder newBuilder = new StringBuilder();
    Bounds bounds = delegate.retrieveCanvasBoundaries();
    if (bounds == null) {
      throw new IllegalArgumentException("No canvas bounds applied");
    }
    newBuilder
        .append("<svg width=\"" + bounds.width)
        .append("\" height=\"" + bounds.height)
        .append("\"")
        .append(" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n");
    HashMap<String, Shape> shapeMap = delegate.retrieveCurrentGameState();
    ArrayList<String> shapes = delegate.retrieveOrderedShapeNames();
    for (String shapeName : shapes) {
      ArrayList<Motion> pertinentMotions = delegate.retrieveMotionsForObjectWithName(shapeName);
      ShapeType shapeType = shapeMap.get(shapeName).type;
      if (shapeType == ShapeType.RECTANGLE) {
        if (pertinentMotions.size() == 0) {
          throw new IllegalArgumentException("No values associated with this Shape.");
        } else {
          Motion originalMotion = pertinentMotions.get(0);
          newBuilder.append("<rect id=\"" + shapeName + "\"");
          newBuilder.append(getRectXML(originalMotion));
          ArrayList<Rotation> anything = delegate.retrieveRotationsForObjectWithName(shapeName);
          applyRectAnimationXML(newBuilder, pertinentMotions, speed, anything);
          newBuilder.append("</rect>\n");
        }
      } else if (shapeType == ShapeType.ELLIPSE) {
        if (pertinentMotions.size() == 0) {
          throw new IllegalArgumentException("No values associated with this Shape.");
        } else {
          Motion originalMotion = pertinentMotions.get(0);
          newBuilder.append("<ellipse id=\"" + shapeName + "\"");
          newBuilder.append(getEllipseXML(originalMotion));
          ArrayList<Rotation> anything = delegate.retrieveRotationsForObjectWithName(shapeName);
          applyEllipseAnimationXML(newBuilder, pertinentMotions, anything);
          newBuilder.append("</ellipse>\n");
        }
      }
    }
    newBuilder.append("</svg>");
    return newBuilder.toString();
  }

  private void applyEllipseAnimationXML(StringBuilder newBuilder,
                                        ArrayList<Motion> pertinentMotions, ArrayList<Rotation> anything) {
    for (int i = 0; i < pertinentMotions.size(); i++) {
      Motion curMotion = pertinentMotions.get(i);
      int ticksPerSecond = curMotion.startComp.tick * 1000 / speed;
      int ticksPerMotion =
          ((curMotion.endComp.tick * 1000 / speed)
              - (curMotion.startComp.tick * 1000 / speed));
      newBuilder.append("<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
          + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"");
      newBuilder.append(" attributeName=\"cx\" from=\"" + curMotion.startComp.x
          + "\" to=\"" + curMotion.endComp.x + "\" fill=\"remove\" />\n");
      newBuilder.append("<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
          + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"");
      newBuilder.append(" attributeName=\"cy\" from=\"" + curMotion.startComp.y + "\" to=\""
          + curMotion.endComp.y + "\" fill=\"remove\" />\n");
      newBuilder.append("<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
          + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"");
      newBuilder.append(" attributeName=\"rx\" from=\"" + curMotion.startComp.width + "\" to=\""
          + curMotion.endComp.width + "\" fill=\"remove\" />\n");
      newBuilder.append("<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
          + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"");
      newBuilder.append(" attributeName=\"ry\" from=\"" + curMotion.startComp.height + "\" to=\""
          + curMotion.endComp.height + "\" fill=\"remove\" />\n");
      newBuilder.append("<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
          + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"");
      newBuilder.append(" attributeName=\"fill\" from=\"rgb(" + curMotion.startComp.color.red
          + "," + curMotion.startComp.color.green + "," + curMotion.startComp.color.blue
          + ")\" to=\"rgb(" + curMotion.endComp.color.red + ","
          + curMotion.endComp.color.green + ","
          + curMotion.endComp.color.blue + ")\"" + " fill=\"remove\" />\n");
    }
    for (Rotation every : anything) {
      Motion accompanyingMotion = findRelevantMotion(every.associatedShape, every.startTick);
      newBuilder.append("<animateTransform attributeName=\"transform\" attributeType=\"XML\" "
          + "type=\"rotate\" from=\"" + every.startRadian + " "
          + (accompanyingMotion.startComp.x + (accompanyingMotion.endComp.width/2))
          + " " + (accompanyingMotion.startComp.y + (accompanyingMotion.endComp.height/2)) + "\""
          + " to=\"" + every.endRadian + " " + accompanyingMotion.endComp.x
          + " " + accompanyingMotion.endComp.y + "\""
          + " dur=\"" + (every.endTick - every.startTick) + "\" repeatCount=\""
          + "1\"/>\n");
    }
  }

  // rcurX + rcurW / 2,
  //                    rcurY + rcurH / 2);

  private String getEllipseXML(Motion originalMotion) {
    return " cx=\"" + originalMotion.startComp.x + "\" cy=\""
        + originalMotion.startComp.y + "\" "
        + "rx=\"" + originalMotion.startComp.width
        + "\" " + "ry=\"" + originalMotion.startComp.height + "\" " + "fill=\"rgb"
        + "(" + originalMotion.startComp.color.red
        + "," + originalMotion.startComp.color.green + ","
        + originalMotion.startComp.color.blue
        + ")\" " + "visibility=\"visible\" >\n";
  }

  private void applyRectAnimationXML(StringBuilder newBuilder,
                                            ArrayList<Motion> pertinentMotions, int speed,
                                            ArrayList<Rotation> rotations) {
    for (int i = 0; i < pertinentMotions.size(); i++) {
      Motion curMotion = pertinentMotions.get(i);
      int ticksPerSecond = curMotion.startComp.tick * 1000 / speed;
      int ticksPerMotion =
          ((curMotion.endComp.tick * 1000 / speed)
              - (curMotion.startComp.tick * 1000 / speed));
      newBuilder.append(
          "<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
              + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"").append(" "
          + "attributeName=\"x\" from=\"" + curMotion.startComp.x + "\" to=\""
          + curMotion.endComp.x + "\" fill=\"freeze\" />\n");
      newBuilder.append("<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
          + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"").append(" "
          + "attributeName=\"y\" from=\"" + curMotion.startComp.y
          + "\" to=\"" + curMotion.endComp.y + "\" fill=\"freeze\" />\n");
      newBuilder.append("<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
          + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"").append(" "
          + "attributeName=\"width\" from=\"" + curMotion.startComp.width + "\" to=\""
          + curMotion.endComp.width + "\" fill=\"freeze\" />\n");
      newBuilder.append("<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
          + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"").append(" "
          + "attributeName=\"height\" from=\"" + curMotion.startComp.height + "\" to=\""
          + curMotion.endComp.height + "\" fill=\"freeze\" />\n");
      newBuilder.append("<animate attributeType=\"xml\" begin=\"" + ticksPerSecond
          + "ms" + "\" " + "dur=\"" + ticksPerMotion + "ms\"").append(" "
          + "attributeName=\"fill\" from=\"rgb(" + curMotion.startComp.color.red
          + "," + curMotion.startComp.color.green + "," + curMotion.startComp.color.blue
          + ")\" to=\"rgb(" + curMotion.endComp.color.red + ","
          + curMotion.endComp.color.green + "," + curMotion.endComp.color.blue
          + ")\"" + " fill=\"freeze\" />\n");
    }
    for (Rotation every : rotations) {
      Motion accompanyingMotion = findRelevantMotion(every.associatedShape, every.startTick);
      newBuilder.append("<animateTransform attributeName=\"transform\" attributeType=\"XML\" "
          + "type=\"rotate\" from=\"" + every.startRadian + " "
          + (accompanyingMotion.startComp.x + (accompanyingMotion.endComp.width/2))
          + " " + (accompanyingMotion.startComp.y + (accompanyingMotion.endComp.height/2)) + "\""
          + " to=\"" + every.endRadian + " " + accompanyingMotion.endComp.x
          + " " + accompanyingMotion.endComp.y + "\""
          + " dur=\"" + (every.endTick - every.startTick) + "\" repeatCount=\""
          + "1\"/>\n");
    }
  }

  private static String getRectXML(Motion originalMotion) {
    return " x=\"" + originalMotion.startComp.x
        + "\" y=\"" + originalMotion.startComp.y
        + "\" " + "width=\"" + originalMotion.startComp.width
        + "\" " + "height=\"" + originalMotion.startComp.height
        + "\" " + "fill=\"rgb" + "(" + originalMotion.startComp.color.red
        + "," + originalMotion.startComp.color.green + ","
        + originalMotion.startComp.color.blue
        + ")\" " + "visibility=\"visible\" >\n";
  }

  /**
   * Refresh the view to reflect any changes in the animator state.
   *
   * @throws UnsupportedOperationException for views that do not have a Visual.
   */
  @Override
  public void refresh() {
    throw new UnsupportedOperationException("This view does not have a Visual.");
  }

  /**
   * Make the view visible to start the animator.
   *
   * @throws UnsupportedOperationException for views that do not have a Visual.
   */
  @Override
  public void makeVisible() {
    return;
  }

  /**
   * Accept a ViewController that owns this View.
   *
   * @param vcd the delegate that owns this view.
   * @throws UnsupportedOperationException for views that do not have an owner.
   */
  @Override
  public void acceptViewController(VCDelegate vcd) {
    throw new UnsupportedOperationException("This view does not have an owner.");
  }

  private Motion findRelevantMotion(String name, Integer tick) {
    ArrayList<Motion> motions = delegate.retrieveMotionsForObjectWithName(name);
    Motion temp = null;
    for (Motion each : motions) {
      if (tick >= each.startTick && tick <= each.endTick) {
        temp = each;
        break;
      }
    }
    return temp;
  }
}
