package cs3500.excellence;

public class Motion {

  //TODOluke may need to add Shape field that this motion correlates with (no big deal tho)
  final String associatedShape;
  //TODOluke potentially reevaluate neccessity of these fields
  final int startTick;
  final int endTick;
  final MotionComponent startComp;
  final MotionComponent endComp;

  Motion(String associatedShape, int startTick, int startX, int startY,
         int startWidth, int startHeight, Color startColor,
         int endTick, int endX, int endY, int endWidth, int endHeight, Color endColor) {
    this.associatedShape = associatedShape;
    this.startTick = startTick;
    this.endTick = endTick;
    this.startComp = new MotionComponent(startTick, startX, startY, startWidth, startHeight, startColor);
    this.endComp = new MotionComponent(endTick, endX, endY, endWidth, endHeight, endColor);
  }

  // TODOLuke -> currently assumming the model translates RGB raw vals into a color object

  private class MotionComponent {
    int tick;
    int x;
    int y;
    int width;
    int height;
    Color color;

    public MotionComponent(int tick, int x, int y, int width, int height, Color color) {
      this.tick = tick;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.color = color;
    }
  }

}























