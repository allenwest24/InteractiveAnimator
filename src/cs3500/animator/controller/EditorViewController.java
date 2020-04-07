package cs3500.animator.controller;

import cs3500.animator.view.IView;
import cs3500.animator.view.ViewUtils;
import cs3500.excellence.*;

public class EditorViewController implements IViewController, VCDelegate {
  IAnimation<ShapeType> model;
  AnimationDelegate<Shape, Motion> delegateRef;
  IView viewObject;

  public EditorViewController(IAnimation<ShapeType> model,
                              AnimationDelegate<Shape, Motion> delegateRef,
                              IView viewObject) {
    this.model = model;
    this.delegateRef = delegateRef;
    this.viewObject = viewObject;
    this.viewObject.acceptViewController(this);
  }

  @Override
  public void startUp() {
    this.viewObject.makeVisible();
  }

  @Override
  public void userRequestsDeleteShape(String s) {
    this.model.deleteShape(s);
  }

  @Override
  public void userRequestsAddShape(ShapeType type, String name) {
    try {
      model.declareShape(type, name);
    }
    catch(IllegalArgumentException e) {
      return;
    }
  }

  @Override
  public boolean userRequestsDeleteKeyFrame(String shapeName, Integer i) {
    try {
      model.deleteKeyFrameFromModel(shapeName, i);
    }
    catch(IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  /**
   * Add throw to java doc.
   */
  @Override
  public String userRequestAddKeyFrameInfo(String shapeName, Integer i) {
    Motion mo = model.deriveKeyFrameInfo(shapeName, i);
    if(mo == null) {
      throw new IllegalArgumentException("No data for shapes!");
    }
    if(mo.startComp.tick == 0 && mo.startComp.x == 0 && mo.startComp.y == 0
        && mo.startComp.width == 0 && mo.startComp.height == 0) {
      return "0,0,0,0,0,0,0,0";
    }
    else if(mo.endComp.tick == 0 && mo.endComp.x == 0 && mo.endComp.y == 0
        && mo.endComp.width == 0 && mo.endComp.height == 0) {
      return "0,0,0,0,0,0,0,0";
    }
    else {
      int curX = ViewUtils.tweener(i, mo.startComp.x,
          mo.endComp.x, mo.startTick, mo.endTick);
      int curY = ViewUtils.tweener(i, mo.startComp.y,
          mo.endComp.y, mo.startTick, mo.endTick);
      int curW = ViewUtils.tweener(i, mo.startComp.width,
          mo.endComp.width, mo.startTick, mo.endTick);
      int curH = ViewUtils.tweener(i, mo.startComp.height,
          mo.endComp.height, mo.startTick, mo.endTick);
      int curR = ViewUtils.tweener(i, mo.startComp.color.red,
          mo.endComp.color.red, mo.startTick, mo.endTick);
      int curB = ViewUtils.tweener(i, mo.startComp.color.blue,
          mo.endComp.color.blue, mo.startTick, mo.endTick);
      int curG = ViewUtils.tweener(i, mo.startComp.color.green,
          mo.endComp.color.green, mo.startTick, mo.endTick);
      return i + "," + curX + "," + curY + "," + curW + "," + curH + ","
          + curR + "," + curG + "," + curB;
    }
  }
}
