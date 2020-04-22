package cs3500.animator.view;

import cs3500.excellence.ShapeType;

/**
 * Delegate Interface to represent a view that owns a sub-view.
 */
public interface ViewDelegate {

  /**
   * Method to execute the visual of this view.
   */
  void play();

  /**
   * Method to pause the visual of this view.
   */
  void pauseAnimation();

  /**
   * Method to reset the visual of this view.
   */
  void resetAnimation();

  /**
   * Method to loop the visual of this view.
   */
  void loopAnimation();

  /**
   * Method to adjust the speed of this view.
   *
   * @param newSpeed the speed that the user requested to change to.
   */
  void setSpeed(int newSpeed);

  /**
   * Method for a sub-view to ask if a Shape exists by the given name.
   *
   * @param name given name of a possible Shape.
   * @return boolean indicating whether such a Shape exists.
   */
  boolean doesShapeExistForName(String name);

  /**
   * Method for a sub-view to attempt to delete a Shape.
   *
   * @param s name of Shape to delete.
   */
  void userRequestsDeleteShape(String s);

  /**
   * Method for a sub-view to attempt to add a Shape.
   *  @param type type of Shape to add.
   * @param name name of the new Shape.
   * @param toLayerAsInt the optional layer to which the new Shape should be added.
   */
  void userRequestsAddShape(ShapeType type, String name, Integer toLayerAsInt);

  /**
   * Method for a sub-view to attempt to delete a KeyFrame.
   *
   * @param shapeName name of the Shape being mutated.
   * @param i         tick at which the KeyFrame lies upon.
   * @return boolean indicating whether the request was successful.
   */
  boolean deleteKeyFrame(String shapeName, Integer i);

  /**
   * Method to return a String representing the current 'tweened' values at a given tick.
   *
   * @param shapeName Shape for the Keyframe lookup.
   * @param i         tick for the KeyFrame lookup.
   * @return user-facing String of said values.
   */
  String canAddKeyFrameAtTick(String shapeName, Integer i);

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
  boolean passNewValuesOnKeyFrame(boolean b, Integer tempx2, Integer tempy2, Integer tempw2,
                                  Integer temph2, Integer tempr2, Integer tempg2, Integer tempb2);

  /**
   * Method to handle user requests to show the slider.
   */
  void sliderVisible();

  /**
   * Method to handle user requests to add a new layer.
   */
  void addLayer();

  /**
   * Method to handle user requests to delete the given layer.
   *
   * @param layer number of layer to delete.
   */
  boolean deleteLayer(Integer layer);

  /**
   * Method to handle user requests to swap two layers.
   *
   * @param layer1 first layer number to swap.
   * @param layer2 second layer number to swap.
   */
  boolean swapLayers(Integer layer1, Integer layer2);
}
