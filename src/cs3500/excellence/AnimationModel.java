package cs3500.excellence;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

public class AnimationModel implements IAnimation {

  private final HashMap<String, AbstractShape> declaredShapes;

  AnimationModel() {
    declaredShapes = new HashMap<String, AbstractShape>();
  }

  // TODOluke ensure non null mentioned in javadoc
  @Override
  public void declareShape(ShapeType type, String name) {
    if (type == null || name == null) {
      throw new IllegalArgumentException("Cannot have null parameters.");
    }
    if (declaredShapes.containsKey(name)) {
      throw new IllegalArgumentException("Already have a shape with provided name");
    }

    switch (type) {
      case SQUARE:
        break;
      case RECTANGLE:
        declaredShapes.put(name, new Rectangle(type));
      default:
        break;
    }

  }

  private void x() {
    //TODOluke ENSURE SHAPES ARE IMMUTABLE
    Collection<AbstractShape> allShapes = this.declaredShapes.values();
    
  }

  @Override
  public void applyMotion(String shapeName, Motion start, Motion end) {

  }
}
