package cs3500.excellence;

import java.util.HashMap;

public class AnimationModel implements IAnimation {

  private final HashMap<String, ShapeType> declaredShapes;

  AnimationModel() {
    declaredShapes = new HashMap<String, ShapeType>();
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
    declaredShapes.put(name, type);

  }
}
