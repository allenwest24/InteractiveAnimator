package cs3500.animator.controller;

import cs3500.excellence.ShapeType;

public interface VCDelegate {
  boolean userRequestsDeleteShape(String s);

  void userRequestsAddShape(ShapeType type, String name);

  boolean userRequestsDeleteKeyFrame(String shapeName, Integer i);

  String userRequestAddKeyFrameInfo(String shapeName, Integer i);

  boolean passNewValuesOnKeyFrameAgain(boolean b, Integer tempx2, Integer tempy2, Integer tempw2,
                                       Integer temph2, Integer tempr2, Integer tempg2,
                                       Integer tempb2);
}
