package cs3500.excellence;

import java.util.ArrayList;

public class Shape extends UserInteraction {

  protected final ShapeType type;
  protected final String name;
  protected final ArrayList<Motion> motions;

  protected Shape(ShapeType type, String name) {
    this.type = type;
    this.name = name;
    motions = new ArrayList<Motion>();
  }

  // Note in javadoc that only shallow copy is necessary here due to Motion being a value (immutable) type.
  protected Shape(Shape shape) {
    ShapeType typeCopy = shape.type;
    this.type = typeCopy;
    String nameCopy = shape.name;
    this.name = nameCopy;
    motions = new ArrayList<Motion>();
    for (Motion each: shape.motions) {
      this.motions.add(each);
    }
  }

  // Can return null
  protected final Motion retrieveLatestMotion() {
    if (this.motions.isEmpty()) {
      return null;
    } else {
      return motions.get(motions.size() - 1);
    }
  }

  //Implicit dependency - first motion on a shape needs to have everything (must be in doc)
  protected final boolean conditionallyAddMotionToShape(Motion motion) {
    if (this.retrieveLatestMotion() == null && !motion.completeMotion()) {
      return false;
    }
    if (motion.completeMotion() && this.retrieveLatestMotion() == null) {
      this.motions.add(motion);
      return true;
    }
    Motion prevMotion = this.retrieveLatestMotion();
    Motion validComputedMotion = Motion.computeNextMotion(motion, prevMotion);
    if (validComputedMotion != null) {
      this.motions.add(validComputedMotion);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public String userMove() {
    return "shape " + name + " " + type.getName(type);
  }

}
