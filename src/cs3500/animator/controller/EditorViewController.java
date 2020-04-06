package cs3500.animator.controller;

import cs3500.animator.view.IView;
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
}
