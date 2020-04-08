package cs3500.animator.controller;

import cs3500.animator.view.IView;
import cs3500.animator.view.ViewUtils;
import cs3500.excellence.*;

/**
 * This is the concrete implementation of a view controller for the animation program.
 * This view controller serves to facilitate the interaction between animator views and their model.
 */
public final class EditorViewController implements IViewController, VCDelegate<ShapeType> {
  IAnimation<ShapeType> model;
  AnimationDelegate<Shape, Motion> delegateRef;
  IView viewObject;
  Integer tickReq;
  Motion motionReq;
  String tempName;

  /**
   * This is a constructor to create an EditorViewController object.
   *
   * @param model       a mutable IAnimation.
   * @param delegateRef an immutable AnimationDelegate for the views.
   * @param viewObject  the view that this controller owns.
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public EditorViewController(IAnimation<ShapeType> model,
                              AnimationDelegate<Shape, Motion> delegateRef,
                              IView viewObject) {
    if (model == null || delegateRef == null || viewObject == null) {
      throw new IllegalArgumentException("Cannot accept null parameters.");
    }
    this.tempName = null;
    this.model = model;
    this.delegateRef = delegateRef;
    this.viewObject = viewObject;
    this.viewObject.acceptViewController(this);
    this.tickReq = null;
    this.motionReq = null;
  }

  /**
   * This method is used to turn over control of the program to this view controller such that
   * it can facilitate the interaction between views and a model.
   */
  @Override
  public void startUp() {
    this.viewObject.makeVisible();
  }

  /**
   * This method handle a request from the user to delete a Shape from the view so that the
   * controller can determine what to do with said request.
   *
   * @param s the name of the Shape that the user would like to delete.
   * @return success or failure based on whether the View controller decides to accept this request.
   */
  @Override
  public boolean userRequestsDeleteShape(String s) {
    return this.model.deleteShape(s);
  }

  /**
   * This method handle a request from the user to add a Shape from the view so that the
   * controller can determine what to do with said request.
   *
   * @param type the generic type of the Shape that the user would like to add.
   * @param name the name of the Shape that the user would like to add.
   */
  @Override
  public void userRequestsAddShape(ShapeType type, String name) {
    try {
      model.declareShape(type, name);
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
   * This method handle a request from the user to delete a KeyFrame from the view so that the
   * controller can determine what to do with said request.
   *
   * @param shapeName the name of the Shape that the user would like to delete a KeyFrame from.
   * @param i         the tick correlating with the key frame the user would like to delete.
   * @return success or failure based on whether the View controller decides to accept this request.
   */
  @Override
  public boolean userRequestsDeleteKeyFrame(String shapeName, Integer i) {
    try {
      model.deleteKeyFrameFromModel(shapeName, i);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  /**
   * This method handle a request from the user to add a KeyFrame to the view so that the
   * controller can determine what to do with said request.
   *
   * @param shapeName the name of the Shape that the user would like to add a KeyFrame to.
   * @param i         the tick correlating with the key frame the user would like to add.
   * @return success or failure based on whether the View controller decides to accept this request.
   * @throws IllegalArgumentException if there is any null parameter.
   */
  @Override
  public String userRequestAddKeyFrameInfo(String shapeName, Integer i) {
    this.tickReq = i;
    this.tempName = shapeName;
    Motion mo = model.deriveKeyFrameInfo(shapeName, i);
    this.motionReq = mo;
    if (mo == null) {
      throw new IllegalArgumentException("No data for shapes!");
    }
    if (mo.startComp.tick == 0 && mo.startComp.x == 0 && mo.startComp.y == 0
        && mo.startComp.width == 0 && mo.startComp.height == 0) {
      return "0,0,0,0,0,0,0";
    } else if (mo.endComp.tick == 0 && mo.endComp.x == 0 && mo.endComp.y == 0
        && mo.endComp.width == 0 && mo.endComp.height == 0) {
      return "0,0,0,0,0,0,0";
    } else {
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
      return curX + "," + curY + "," + curW + "," + curH + ","
          + curR + "," + curG + "," + curB;
    }
  }

  /**
   * Method to handle user requests to add key frames from the view.
   *
   * @param b      boolean value indicating whether or not the user has fulfilled all parameters of
   *               this request correctly.
   * @param tempx2 the nullable Integer to represent the new x coordinate associated with this key
   *               frame.
   * @param tempy2 the nullable Integer to represent the new y coordinate associated with this key
   *               frame.
   * @param tempw2 the nullable Integer to represent the new width value associated with this key
   *               frame.
   * @param temph2 the nullable Integer to represent the new height value associated with this key
   *               frame.
   * @param tempr2 the nullable Integer to represent the new r Color value associated with this key
   *               frame.
   * @param tempg2 the nullable Integer to represent the new g Color value associated with this key
   *               frame.
   * @param tempb2 the nullable Integer to represent the new b Color value associated with this key
   *               frame.
   * @return boolean indicating whether the controller accepts this request.
   */
  @Override
  public boolean passNewValuesOnKeyFrameAgain(boolean b, Integer tempx2, Integer tempy2,
                                              Integer tempw2, Integer temph2, Integer tempr2,
                                              Integer tempg2, Integer tempb2) {
    if (!b) {
      this.motionReq = null;
      this.tickReq = null;
      this.tempName = null;
      return false;
    }
    try {
      this.model.mutateKeyFrame(this.tempName, this.tickReq, tempx2, tempy2,
          tempw2, temph2, tempr2,
          tempg2, tempb2);
    } catch (IllegalArgumentException e) {
      return false;
    }
    this.motionReq = null;
    this.tickReq = null;
    this.tempName = null;
    return true;
  }
}
