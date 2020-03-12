package cs3500.excellence;

/**
 * Enum for representing possible shapeTypes.
 *
 * <p>Simply adding a new case and associated String to the getName method completes new shape
 * implementation without any other changes in the program.
 */
public enum ShapeType {
  RECTANGLE, ELLIPSE;

  protected static final String getName(ShapeType type) {
    switch (type) {
      case RECTANGLE:
        return "rectangle";
      case ELLIPSE:
        return "ellipse";
      default:
        throw new UnsupportedOperationException("No associated type.");
    }
  }
}
