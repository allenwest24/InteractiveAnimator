package cs3500.animator.controller;

import cs3500.animator.view.IView;
import cs3500.excellence.*;

public class EditorViewController implements IViewController {
  IAnimation<ShapeType> model;
  AnimationDelegate<Shape, Motion> delegateRef;
  IView viewObject;

  public EditorViewController(IAnimation<ShapeType> model,
                              AnimationDelegate<Shape, Motion> delegateRef,
                              IView viewObject) {
    this.model = model;
    this.delegateRef = delegateRef;
    this.viewObject = viewObject;
  }

  @Override
  public void startUp() {

  }
}
