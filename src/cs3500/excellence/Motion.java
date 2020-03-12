package cs3500.excellence;

/**
 * Immutable value type class representing a motion.
 */
public class Motion extends UserInteraction {
  protected final String associatedShape;
  protected final int startTick;
  protected final int endTick;
  protected final MotionComponent startComp;
  protected final MotionComponent endComp;

  /**
   * Protected constructor for the motion object.
   *
   * @throws IllegalArgumentException in the case of a null associatedShape String.
   */
  protected Motion(String associatedShape, int startTick, Integer startX, Integer startY,
                   Integer startWidth, Integer startHeight, Color startColor,
                   int endTick, Integer endX, Integer endY, Integer endWidth, Integer endHeight,
                   Color endColor) {
    if (associatedShape == null) {
      throw new IllegalArgumentException("No shape name provided.");
    }
    this.associatedShape = associatedShape;
    this.startTick = startTick;
    this.endTick = endTick;
    this.startComp = new MotionComponent(startTick, startX, startY, startWidth, startHeight,
            startColor);
    this.endComp = new MotionComponent(endTick, endX, endY, endWidth, endHeight, endColor);
  }

  protected static final Motion computeNextMotion(Motion proposedMotion, Motion current) {
    if (current.endTick != proposedMotion.startTick) {
      return null;
    }
    int newStartTick = proposedMotion.startTick;
    int newEndTick = proposedMotion.endTick;
    String shape = proposedMotion.associatedShape;
    int newXStart = current.endComp.x;
    int newYStart = current.endComp.y;
    int newXEnd = current.endComp.x;
    int newYEnd = current.endComp.y;
    int newStartWidth = current.endComp.width;
    int newStartHeight = current.endComp.height;
    int newEndWidth = current.endComp.width;
    int newEndHeight = current.endComp.height;
    Color newStartCol = current.endComp.color;
    Color newEndCol = current.endComp.color;
    if (!proposedMotion.startComp.noCoords()) {
      if (!current.endComp.x.equals(proposedMotion.startComp.x)
              || !current.endComp.y.equals(proposedMotion.startComp.y)) {
        return null;
      }
      newXEnd = proposedMotion.endComp.x;
      newYEnd = proposedMotion.endComp.y;
    }
    if (!proposedMotion.startComp.noColor()) {
      if (!current.endComp.color.deriveEquivalency(proposedMotion.startComp.color)) {
        return null;
      } else {
        newEndCol = proposedMotion.endComp.color;
      }
    }
    if (!proposedMotion.startComp.noWidth()) {
      if (!current.endComp.width.equals(proposedMotion.startComp.width)) {
        return null;
      } else {
        newEndWidth = proposedMotion.endComp.width;
      }
    }
    if (!proposedMotion.startComp.noHeight()) {
      if (!current.endComp.height.equals(proposedMotion.startComp.width)) {
        return null;
      } else {
        newEndHeight = proposedMotion.endComp.height;
      }
    }
    return new Motion(shape, newStartTick, newXStart, newYStart, newStartWidth, newStartHeight,
            newStartCol, newEndTick, newXEnd, newYEnd, newEndWidth, newEndHeight, newEndCol);
  }

  protected final boolean completeMotion() {
    return startComp.completeMotionComponent() && endComp.completeMotionComponent();
  }

  @Override
  protected String userMove() {
    return "motion " + associatedShape + " "
            + startComp.getInformation() + " " + endComp.getInformation();
  }

  protected class MotionComponent {
    final int tick;
    final Integer x;
    final Integer y;
    final Integer width;
    final Integer height;
    final Color color;

    protected MotionComponent(int tick, Integer x, Integer y, Integer width, Integer height,
                              Color color) {
      this.tick = tick;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.color = color;
    }

    protected final boolean completeMotionComponent() {
      return x != null && y != null && width != null && height != null && color != null;
    }

    protected final String getInformation() {
      return tick + " " + x + " " + y + " " + width + " " + height + " " + color.red
              + " " + color.green + " " + color.blue;
    }

    protected final boolean noColor() {
      return this.color == null;
    }

    protected final boolean noCoords() {
      return this.x == null && this.y == null;
    }

    protected final boolean noWidth() {
      return this.width == null;
    }

    protected final boolean noHeight() {
      return this.height == null;
    }
  }
}