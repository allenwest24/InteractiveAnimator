package cs3500.animator.controller;

/**
 * This Interface represents view access to a ViewController such that the view can communicate
 * user actions.
 */
public interface VCDelegate<R> {

  /**
   * This method handle a request from the user to delete a Shape from the view so that the
   * controller can determine what to do with said request.
   *
   * @param s the name of the Shape that the user would like to delete.
   * @return success or failure based on whether the View controller decides to accept this request.
   */
  boolean userRequestsDeleteShape(String s);

  /**
   * This method handle a request from the user to add a Shape from the view so that the
   * controller can determine what to do with said request.
   *
   * @param type the generic type of the Shape that the user would like to add.
   * @param name the name of the Shape that the user would like to add.
   */
  void userRequestsAddShape(R type, String name);

  /**
   * This method handle a request from the user to delete a KeyFrame from the view so that the
   * controller can determine what to do with said request.
   *
   * @param shapeName the name of the Shape that the user would like to delete a KeyFrame from.
   * @param i         the tick correlating with the key frame the user would like to delete.
   * @return success or failure based on whether the View controller decides to accept this request.
   */
  boolean userRequestsDeleteKeyFrame(String shapeName, Integer i);

  /**
   * This method handle a request from the user to add a KeyFrame to the view so that the
   * controller can determine what to do with said request.
   *
   * @param shapeName the name of the Shape that the user would like to add a KeyFrame to.
   * @param i         the tick correlating with the key frame the user would like to add.
   * @throws IllegalArgumentException if there is any null parameter.
   * @return success or failure based on whether the View controller decides to accept this request.
   */
  String userRequestAddKeyFrameInfo(String shapeName, Integer i);

  /**
   * Method to handle user requests to add key frames from the view.
   *
   * @param b      boolean value indicating whether or not the user has fulfilled all parameters of this
   *               request correctly.
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
  boolean passNewValuesOnKeyFrameAgain(boolean b, Integer tempx2, Integer tempy2, Integer tempw2,
                                       Integer temph2, Integer tempr2, Integer tempg2,
                                       Integer tempb2);
}
