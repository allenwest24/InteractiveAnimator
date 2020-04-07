package cs3500.animator.view;

import cs3500.excellence.ShapeType;

public interface ViewDelegate {
  void play();

  void pauseAnimation();

  // Can be null to reset to beginning.
  void resetAnimation();

  void loopAnimation();

  void setSpeed(int newSpeed);

  boolean doesShapeExistForName(String name);

  void userRequestsDeleteShape(String s);

  void userRequestsAddShape(ShapeType type, String name);

  boolean deleteKeyFrame(String shapeName, Integer i);

  String canAddKeyFrameAtTick(String shapeName, Integer i);

  boolean passNewValuesOnKeyFrame(boolean b, Integer tempx2, Integer tempy2, Integer tempw2,
                               Integer temph2, Integer tempr2, Integer tempg2, Integer tempb2);
}
