package cs3500.excellence;

import java.util.ArrayList;

/**
 * Immutable value type class representing a shape.
 */
public class Shape extends UserInteraction {

  protected final ShapeType type;
  protected final String name;
  protected final ArrayList<Motion> motions;

  /**
   * Protected constructor for creating an immutable shape.
   *
   * @param type Non-Null ShapeType representing a shape type.
   * @param name Non-Null String representing a shape name.
   * @throws IllegalArgumentException for null parameters.
   */
  protected Shape(ShapeType type, String name) {
    if (type == null || name == null) {
      throw new IllegalArgumentException("Null parameter detected");
    }
    this.type = type;
    this.name = name;
    motions = new ArrayList<Motion>();
  }

  protected Shape(Shape shape) {
    ShapeType typeCopy = shape.type;
    this.type = typeCopy;
    String nameCopy = shape.name;
    this.name = nameCopy;
    motions = new ArrayList<Motion>();
    for (Motion each : shape.motions) {
      this.motions.add(each);
    }
  }

  // Nullable function (optional emulation)
  protected final Motion retrieveLatestMotion() {
    if (this.motions.isEmpty()) {
      return null;
    } else {
      return motions.get(motions.size() - 1);
    }
  }

  protected final boolean conditionallyAddMotionToShape(Motion motion) {
    if (this.retrieveLatestMotion() == null && !motion.completeMotion()) {
      return false;
    }
    if (motion.completeMotion() && this.retrieveLatestMotion() == null) {
      this.motions.add(motion);
      return true;
    }
    Motion prevMotion = this.retrieveLatestMotion();
    Motion validComputedMotion = motion;
    if (!motion.completeMotion()) {
      validComputedMotion = Motion.computeNextMotion(motion, prevMotion);
    }
    if (validComputedMotion != null) {
      this.motions.add(validComputedMotion);
      return true;
    } else {
      return false;
    }
  }

  @Override
  protected String userMove() {
    return "shape " + name + " " + type.getName(type);
  }

}
