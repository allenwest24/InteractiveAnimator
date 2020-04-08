package cs3500.excellence;

/**
 * An interface representing the mandatory functionality for an Animation model.
 *
 * <p>This interface is parameterized generically for loose coupling.
 */
public interface IAnimation<K> {

  /**
   * Method to add a new Shape to the Animation model.
   *
   * @param type a generic type representing some form of shape.
   * @param name a non-null String representing the name to be associated with the new shape.
   * @throws IllegalArgumentException if the provided String is null or already in use.
   * @throws IllegalArgumentException if the type is unhandled or null.
   */
  void declareShape(K type, String name);

  /**
   * Method to apply various modifications (motions) to previously declared shapes.
   *
   * @param startTick   int to represent the starting tick.
   * @param endTick     int to represent the ending tick.
   * @param shapeName   non-null String representing the name of the Shape the motion is applied
   *                    to.
   * @param startX      Nullable Integer representing the optional startX coordinate of this
   *                    motion.
   * @param startY      Nullable Integer representing the optional startY coordinate of this
   *                    motion.
   * @param endX        Nullable Integer representing the optional endX coordinate of this motion.
   * @param endY        Nullable Integer representing the optional endY coordinate of this motion.
   * @param startWidth  Nullable Integer representing the optional startWidth value of this motion.
   * @param startHeight Nullable Integer representing the optional startHeight value of this
   *                    motion.
   * @param endWidth    Nullable Integer representing the optional endWidth value of this motion.
   * @param endHeight   Nullable Integer representing the optional endHeight value of this motion.
   * @param red         Nullable Integer representing the optional startRed RGB value of this
   *                    motion.
   * @param green       Nullable Integer representing the optional startGreen RGB value of this
   *                    motion.
   * @param blue        Nullable Integer representing the optional startBlue RGB value of this
   *                    motion.
   * @param redEnd      Nullable Integer representing the optional endRed RGB value of this motion.
   * @param greenEnd    Nullable Integer representing the optional endGreen RGB value of this
   *                    motion.
   * @param blueEnd     Nullable Integer representing the optional endBlue RGB value of this
   *                    motion.
   * @throws IllegalArgumentException if the provided String shapeName is null or already in use.
   * @throws IllegalArgumentException if the type is unhandled or null.
   * @throws IllegalArgumentException if only some coordinates are provided (only start, only end,
   *                                  or any other incomplete combination).
   * @throws IllegalArgumentException if only some RGB values are provided (only start, only end, or
   *                                  any other incomplete combination).
   * @throws IllegalArgumentException if only some a startHeight or endHeight is provided, or vice
   *                                  versa.
   * @throws IllegalArgumentException if only some a startHeight or endHeight is provided, or vice
   *                                  versa.
   * @throws IllegalArgumentException if the motion is incompatible with the most recent motion of a
   *                                  shape, or if an incomplete motion is the first to be applied
   *                                  to a given shape.
   */
  void applyMotion(int startTick, int endTick, String shapeName,
                   Integer startX, Integer startY, Integer endX, Integer endY,
                   Integer startWidth, Integer startHeight, Integer endWidth, Integer endHeight,
                   Integer red, Integer green, Integer blue, Integer redEnd, Integer greenEnd,
                   Integer blueEnd);

  /**
   * A method for informing the implementor of this object of Bounds associated with an animation.
   *
   * @param bounds a nullable Bounds object.
   */
  void setBounds(Bounds bounds);

  /**
   * Delete a Shape from the Model.
   *
   * @param s the name of the Shape to delete.
   * @return true for successful deletion, else false.
   */
  boolean deleteShape(String s);

  /**
   * Delete a KeyFrame at the given tick from the given Shape.
   *
   * @param shapeName name of Shape to delete KeyFrame from.
   * @param i         tick of the KeyFrame to delete.
   * @throws IllegalArgumentException for invalid KeyFrame or null param.
   */
  void deleteKeyFrameFromModel(String shapeName, Integer i);

  /**
   * Method for the controller to retrieve an immutable representation of a KeyFrame on the given
   * Shape, for the given tick.
   *
   * @param shapeName name of Shape.
   * @param i         tick of the KeyFrame.
   * @return Motion representing the KeyFrame, or null if such a KeyFrame does not exist.
   */
  Motion deriveKeyFrameInfo(String shapeName, Integer i);

  /**
   * Method for the controller to add or mutate a KeyFrame for the specified Shape.
   *
   * @param shapeName name of Shape.
   * @param tickReq   tick of the KeyFrame.
   * @param tempx2    the nullable Integer to represent the new x coordinate associated with this
   *                  key frame.
   * @param tempy2    the nullable Integer to represent the new y coordinate associated with this
   *                  key frame.
   * @param tempw2    the nullable Integer to represent the new width value associated with this
   *                  key frame.
   * @param temph2    the nullable Integer to represent the new height value associated with this
   *                  key frame.
   * @param tempr2    the nullable Integer to represent the new r Color value associated with this
   *                  key frame.
   * @param tempg2    the nullable Integer to represent the new g Color value associated with this
   *                  key  frame.
   * @param tempb2    the nullable Integer to represent the new b Color value associated with this
   *                  key  frame.
   * @throws IllegalArgumentException for null params or invalid KeyFrames.
   */
  void mutateKeyFrame(String shapeName, Integer tickReq, Integer tempx2,
                      Integer tempy2, Integer tempw2, Integer temph2, Integer tempr2,
                      Integer tempg2, Integer tempb2);
}
