package cs3500.excellence;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class representing the Model for the exCELlence animator.
 *
 * <p>Inheritance note: It is safe to subclass this object, since all methods and fields
 * that are not required by the implemented interfaces are declared private.
 */
public class AnimationModel implements IAnimation<ShapeType>, AnimationDelegate<Shape, Motion> {

  private HashMap<String, Shape> declaredShapes;
  private ArrayList<UserInteraction> allMoves;

  /**
   * Public constructor to create a model for an exCELlence animator.
   */
  public AnimationModel() {
    declaredShapes = new HashMap<String, Shape>();
    allMoves = new ArrayList<UserInteraction>();
  }

  /**
   * Method to add a new Shape to the Animation model.
   *
   * @param type a generic type representing some form of shape.
   * @param name a non-null String representing the name to be associated with the new shape.
   * @throws IllegalArgumentException if the provided String is null or already in use.
   * @throws IllegalArgumentException if the type is unhandled or null.
   */
  @Override
  public void declareShape(ShapeType type, String name) {
    if (type == null || name == null) {
      throw new IllegalArgumentException("Cannot have null parameters.");
    }
    if (declaredShapes.containsKey(name)) {
      throw new IllegalArgumentException("Already have a shape with provided name");
    }
    Shape shape = new Shape(type, name);
    declaredShapes.put(name, shape);
    allMoves.add(shape);
  }

  @Override
  public void applyMotion(int startTick, int endTick, String shapeName, Integer startX,
                          Integer startY, Integer endX, Integer endY, Integer startWidth,
                          Integer startHeight, Integer endWidth, Integer endHeight, Integer red,
                          Integer green, Integer blue, Integer redEnd, Integer greenEnd,
                          Integer blueEnd) {
    if (shapeName == null) {
      throw new IllegalArgumentException("No shape name provided.");
    }
    Shape relevantShape = this.declaredShapes.get(shapeName);
    if (relevantShape == null) {
      throw new IllegalArgumentException("No declared shape with that name.");
    }
    Motion potentialMotion = this.optionallyDeriveMotion(startTick, endTick, shapeName, startX,
            startY, endX, endY, startWidth, startHeight, endWidth, endHeight, red, green, blue,
            redEnd, greenEnd, blueEnd);
    if (potentialMotion == null || !relevantShape.conditionallyAddMotionToShape(potentialMotion)) {
      throw new IllegalArgumentException("Unacceptable motion.");
    } else {
      allMoves.add(relevantShape.retrieveLatestMotion());
    }
  }

  // This bad boy explicitly enforces all the "implicit" dependencies they talk about
  private Motion optionallyDeriveMotion(int startTick, int endTick, String shapeName,
                                        Integer startX, Integer startY, Integer endX, Integer endY,
                                        Integer startWidth, Integer startHeight, Integer endWidth,
                                        Integer endHeight, Integer red, Integer green, Integer blue,
                                        Integer redEnd, Integer greenEnd, Integer blueEnd) {
    Color startColor = null;
    Color endColor = null;
    boolean color = true;
    // Discuss with Allen, do we think this bad mothafucka should be <=? Can we make
    // alterations to a shape if it occurs when startTick == endTick? Instant? Probably not...
    if (startTick > endTick) {
      return null;
    }
    // IMPLICIT DEPENDENCY -> must be added to applyMotion javadoc in interface
    // We either get all color vals or none
    if (red == null || blue == null || green == null
            || redEnd == null || greenEnd == null || blueEnd == null) {
      if (red != null || blue != null || green != null
              || redEnd != null || greenEnd != null || blueEnd != null) {
        return null;
      } else {
        color = false;
      }
    } else {
      try {
        startColor = new Color(red, green, blue);
        endColor = new Color(redEnd, greenEnd, blueEnd);
      } catch (IllegalStateException e) {
        return null;
      }
    }
    // IMPLICIT DEPENDENCIES
    // Height / Width can be independent of one another
    if (startHeight == null || endHeight == null) {
      if (startHeight != null || endHeight != null) {
        return null;
      }
    }
    if (startWidth == null || endWidth == null) {
      if (startWidth != null || endWidth != null) {
        return null;
      }
    }
    if (startX == null || startY == null || endX == null || endY == null) {
      if (startX != null || startY != null || endX != null || endY != null) {
        return null;
      }
    }
    return new Motion(shapeName, startTick, startX, startY, startWidth, startHeight, startColor,
            endTick, endX, endY, endWidth, endHeight, endColor);
  }

  @Override
  public String getStringAnimation() {
    String acc = "";
    for (UserInteraction each : this.allMoves) {
      if (acc.equals("")) {
        acc = acc + each.userMove();
      } else {
        acc = acc + "\n" + each.userMove();
      }
    }
    return acc;
  }

  @Override
  public HashMap<String, Shape> retrieveCurrentGameState() {
    HashMap<String, Shape> safeCopy = new HashMap<String, Shape>();
    for (String shapeName: this.declaredShapes.keySet()) {
      Shape safeShapeCopy = new Shape(this.declaredShapes.get(shapeName));
      String safeStringCopy = shapeName;
      safeCopy.put(safeStringCopy, safeShapeCopy);
    }
    return safeCopy;
  }

  @Override
  public ArrayList<Motion> retrieveMotionsForObjectWithName(String name) {
    HashMap<String, Shape> safeCopy = this.retrieveCurrentGameState();
    if (name == null || !safeCopy.containsKey(name)) {
      throw new IllegalArgumentException("Null or invalid parameter.");
    } else {
      return safeCopy.get(name).motions;
    }
  }
}










