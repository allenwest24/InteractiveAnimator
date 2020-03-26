package cs3500.animator.view;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Bounds;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;
import cs3500.excellence.ShapeType;

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
    for (String shapeName : shapeMap.keySet()) {
      ArrayList<Motion> pertinentMotions = delegate.retrieveMotionsForObjectWithName(shapeName);
      ShapeType shapeType = shapeMap.get(shapeName).type;
      if (shapeType == ShapeType.RECTANGLE) {
        if (pertinentMotions.size() == 0) {
          throw new IllegalArgumentException("No values associated with this Shape.");
        } else {
          Motion originalMotion = pertinentMotions.get(0);
          newBuilder.append("<rect id=\"" + shapeName + "\"");
          newBuilder.append(getRectXML(originalMotion));
          applyRectAnimationXML(newBuilder, pertinentMotions, speed);
          newBuilder.append("</rect>\n");
        }
      } else if (shapeType == ShapeType.ELLIPSE) {
        if (pertinentMotions.size() == 0) {
          throw new IllegalArgumentException("No values associated with this Shape.");
        } else {
          Motion originalMotion = pertinentMotions.get(0);
          newBuilder.append("<ellipse id=\"" + shapeName + "\"");
          newBuilder.append(getEllipseXML(originalMotion));
          applyEllipseAnimationXML(newBuilder, pertinentMotions);
          newBuilder.append("</ellipse>\n");
        }
      }
    }
    newBuilder.append("</svg>");
    return newBuilder.toString();
  }

  private void applyEllipseAnimationXML(StringBuilder newBuilder,
                                        ArrayList<Motion> pertinentMotions) {
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
  }

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

  private static void applyRectAnimationXML(StringBuilder newBuilder,
                                            ArrayList<Motion> pertinentMotions, int speed) {
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

  @Override
  public void refresh() {
    return;
  }

  @Override
  public void makeVisible() {
    return;
  }
}
