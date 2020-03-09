package cs3500.excellence;

public class Rectangle extends AbstractShape {

  protected Rectangle(ShapeType type) {
    super(type);
  }

  @Override
  int positionAtTick(int tick) {
    Motion pertinentMotion = null;
    for (Motion each: this.motions) {
      // Is given tick in the range of this motion?
    }
  }


}
