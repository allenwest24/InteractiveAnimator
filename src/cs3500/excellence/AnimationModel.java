package cs3500.excellence;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.animator.util.AnimationBuilder;

/**
 * A class representing the Model for the exCELlence animator.
 *
 * <p>Inheritance note: It is safe to subclass this object, since all methods and fields
 * that are not required by the implemented interfaces are declared private.
 */
public class AnimationModel implements IAnimation<ShapeType>, AnimationDelegate<Shape, Motion> {

  private HashMap<String, Shape> declaredShapes;
  private ArrayList<UserInteraction> allMoves;
  private Bounds bounds;

  /**
   * Public constructor to create a model for an exCELlence animator.
   */
  public AnimationModel() {
    declaredShapes = new HashMap<String, Shape>();
    allMoves = new ArrayList<UserInteraction>();
    bounds = null;
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

  /**
   * Method to apply various modifications (motions) to previously declared shapes.
   *
   * @param startTick   int to represent the starting tick.
   * @param endTick     int to represent the ending tick.
   * @param shapeName   non-null String representing the name of the Shape the motion is applied
   *                    to.
   * @param startX      Nullable Integer representing the optional startX coordinate of this
   *                    motion.
   * @param startY      Nullable Integer representing the optional startY coordinate of this
   *                    motion.
   * @param endX        Nullable Integer representing the optional endX coordinate of this motion.
   * @param endY        Nullable Integer representing the optional endY coordinate of this motion.
   * @param startWidth  Nullable Integer representing the optional startWidth value of this motion.
   * @param startHeight Nullable Integer representing the optional startHeight value of this
   *                    motion.
   * @param endWidth    Nullable Integer representing the optional endWidth value of this motion.
   * @param endHeight   Nullable Integer representing the optional endHeight value of this motion.
   * @param red         Nullable Integer representing the optional startRed RGB value of this
   *                    motion.
   * @param green       Nullable Integer representing the optional startGreen RGB value of this
   *                    motion.
   * @param blue        Nullable Integer representing the optional startBlue RGB value of this
   *                    motion.
   * @param redEnd      Nullable Integer representing the optional endRed RGB value of this motion.
   * @param greenEnd    Nullable Integer representing the optional endGreen RGB value of this
   *                    motion.
   * @param blueEnd     Nullable Integer representing the optional endBlue RGB value of this
   *                    motion.
   * @throws IllegalArgumentException if the provided String shapeName is null or already in use.
   * @throws IllegalArgumentException if the type is unhandled or null.
   * @throws IllegalArgumentException if only some coordinates are provided (only start, only end,
   *                                  or any other incomplete combination).
   * @throws IllegalArgumentException if only some RGB values are provided (only start, only end, or
   *                                  any other incomplete combination).
   * @throws IllegalArgumentException if only some a startWidth or endWidth is provided, or vice
   *                                  versa.
   * @throws IllegalArgumentException if only some a startHeight or endHeight is provided, or vice
   *                                  versa.
   * @throws IllegalArgumentException if the motion is incompatible with the most recent motion of a
   *                                  shape, or if an incomplete motion is the first to be applied
   *                                  to a given shape.
   * @throws IllegalArgumentException for invalid height or width dimensions (less than 0).
   */
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
      String message = potentialMotion == null ? "Null motion" : "Motion not accepted";
      throw new IllegalArgumentException(message);
    } else {
      allMoves.add(relevantShape.retrieveLatestMotion());
    }
  }

  // TODOluke specify non null bounds in javadoc
  @Override
  public void setBounds(Bounds bounds) {
    this.bounds = bounds;
  }

  //TODOluke get rid of this, was for testing before architecture worked fully
  @Override
  public String stringRep() {
    return this.getStringAnimation();
  }


  private Motion optionallyDeriveMotion(int startTick, int endTick, String shapeName,
                                        Integer startX, Integer startY, Integer endX, Integer endY,
                                        Integer startWidth, Integer startHeight, Integer endWidth,
                                        Integer endHeight, Integer red, Integer green, Integer blue,
                                        Integer redEnd, Integer greenEnd, Integer blueEnd) {
    Color startColor = null;
    Color endColor = null;
    boolean color = true;
    boolean height = true;
    boolean width = true;
    if (startTick > endTick) {
      return null;
    }
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
    if (startHeight == null || endHeight == null) {
      if (startHeight != null || endHeight != null) {
        return null;
      } else {
        height = false;
      }
    }
    if (startWidth == null || endWidth == null) {
      if (startWidth != null || endWidth != null) {
        return null;
      } else {
        width = false;
      }
    }
    if (startX == null || startY == null || endX == null || endY == null) {
      if (startX != null || startY != null || endX != null || endY != null) {
        return null;
      }
    }
    if (height) {
      if (startHeight < 0 || endHeight < 0) {
        throw new IllegalArgumentException("Invalid dimensions");
      }
    }
    if (width) {
      if (startWidth < 0 || endWidth < 0) {
        throw new IllegalArgumentException("Invalid dimensions");
      }
    }
    return new Motion(shapeName, startTick, startX, startY, startWidth, startHeight, startColor,
            endTick, endX, endY, endWidth, endHeight, endColor);
  }

  /**
   * Method to retrieve the current state of the animation, represented as a String.
   */
  @Override
  public String getStringAnimation() {
    String acc = this.bounds.userMove();
    for (UserInteraction each : this.allMoves) {
      if (acc.equals("")) {
        acc = acc + each.userMove();
      } else {
        acc = acc + "\n" + each.userMove();
      }
    }
    return acc;
  }

  /**
   * Method to retrieve a COPY-SAFE representation of the state of the animation, represented as a
   * HashMap of Object names mapped to generic types.
   *
   * <p>All objects that implement this interface should only pass immutable or copy-safe types via
   * this method.
   */
  @Override
  public HashMap<String, Shape> retrieveCurrentGameState() {
    HashMap<String, Shape> safeCopy = new HashMap<String, Shape>();
    for (String shapeName : this.declaredShapes.keySet()) {
      Shape safeShapeCopy = new Shape(this.declaredShapes.get(shapeName));
      String safeStringCopy = shapeName;
      safeCopy.put(safeStringCopy, safeShapeCopy);
    }
    return safeCopy;
  }

  /**
   * Retrieve the K associated with the object correlating with the NON-NULL String name.
   *
   * @param name a non-null String representing the name to be associated with the new shape.
   * @throws IllegalArgumentException if the provided String is null.
   */
  @Override
  public ArrayList<Motion> retrieveMotionsForObjectWithName(String name) {
    HashMap<String, Shape> safeCopy = this.retrieveCurrentGameState();
    if (name == null || !safeCopy.containsKey(name)) {
      throw new IllegalArgumentException("Null or invalid parameter.");
    } else {
      return safeCopy.get(name).motions;
    }
  }

  @Override
  public Bounds retrieveCanvasBoundaries() {
    return this.bounds;
  }

  public static final class Builder implements AnimationBuilder<IAnimation<ShapeType>> {

    private static AnimationModel model = new AnimationModel();

    /**
     * Constructs a final document.
     *
     * @return the newly constructed document
     */

    @Override
    public IAnimation<ShapeType> build() {
      IAnimation<ShapeType> retVal = this.model;
      this.model = new AnimationModel();
      return retVal;
    }

    /**
     * Specify the bounding box to be used for the animation.
     *
     * @param x      The leftmost x value
     * @param y      The topmost y value
     * @param width  The width of the bounding box
     * @param height The height of the bounding box
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<IAnimation<ShapeType>> setBounds(int x, int y, int width, int height) {
      Bounds bounds = null;
      try {
        bounds = new Bounds(x, y, width, height);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
      if (bounds != null) {
        this.model.setBounds(bounds);
      }
      return this;
    }

    /**
     * Adds a new shape to the growing document.
     *
     * @param name The unique name of the shape to be added. No shape with this name should already
     *             exist.
     * @param type The type of shape (e.g. "ellipse", "rectangle") to be added. The set of supported
     *             shapes is unspecified, but should include "ellipse" and "rectangle" as a
     *             minimum.
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<IAnimation<ShapeType>> declareShape(String name, String type) {
      ShapeType potentialType = ShapeType.optionallyDeriveShapeType(type);
      if (potentialType != null) {
        try {
          this.model.declareShape(potentialType, name);
        } catch (IllegalArgumentException e) {
          System.out.println("Unsupported Shape Command");
        }
      }
      return this;
    }

    /**
     * Adds a transformation to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t1   The start time of this transformation
     * @param x1   The initial x-position of the shape
     * @param y1   The initial y-position of the shape
     * @param w1   The initial width of the shape
     * @param h1   The initial height of the shape
     * @param r1   The initial red color-value of the shape
     * @param g1   The initial green color-value of the shape
     * @param b1   The initial blue color-value of the shape
     * @param t2   The end time of this transformation
     * @param x2   The final x-position of the shape
     * @param y2   The final y-position of the shape
     * @param w2   The final width of the shape
     * @param h2   The final height of the shape
     * @param r2   The final red color-value of the shape
     * @param g2   The final green color-value of the shape
     * @param b2   The final blue color-value of the shape
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<IAnimation<ShapeType>> addMotion(String name, int t1, int x1, int y1,
                                                             int w1, int h1, int r1, int g1, int b1,
                                                             int t2, int x2, int y2, int w2, int h2,
                                                             int r2, int g2, int b2) {
      try {
        this.model.applyMotion(t1, t2, name, x1, y1, x2, y2, w1, h1, w2,
                h2, r1, g1, b1, r2, g2, b2);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
      return this;
    }

    /**
     * Adds an individual keyframe to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t    The time for this keyframe
     * @param x    The x-position of the shape
     * @param y    The y-position of the shape
     * @param w    The width of the shape
     * @param h    The height of the shape
     * @param r    The red color-value of the shape
     * @param g    The green color-value of the shape
     * @param b    The blue color-value of the shape
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<IAnimation<ShapeType>> addKeyframe(String name, int t, int x, int y,
                                                               int w, int h, int r, int g, int b) {
      return this;
    }
  }
}