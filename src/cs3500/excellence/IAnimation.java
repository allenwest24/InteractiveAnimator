package cs3500.excellence;

//TODOluke javadoc
public interface IAnimation {

  //TODOluke javadoc
  void declareShape(ShapeType type, String name);

//  # describes the motions of shape R, between two moments of animation:
//          # t == tick
//# (x,y) == position
//# (w,h) == dimensions
//# (r,g,b) == color (with values between 0 and 255)

  void applyMotion(String shapeName, Motion start, Motion end);



}
