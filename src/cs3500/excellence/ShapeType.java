package cs3500.excellence;

// Javadoc
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
