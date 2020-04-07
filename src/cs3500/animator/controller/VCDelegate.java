package cs3500.animator.controller;

import cs3500.excellence.ShapeType;

public interface VCDelegate {
  void userRequestsDeleteShape(String s);

  void userRequestsAddShape(ShapeType type, String name);

  boolean userRequestsDeleteKeyFrame(String shapeName, Integer i);

  String userRequestAddKeyFrameInfo(String shapeName, Integer i);
}
