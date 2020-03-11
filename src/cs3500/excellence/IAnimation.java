package cs3500.excellence;

/**
 * An interface representing the mandatory functionality for an Animation model.
 *
 * This interface is parameterized generically for loose coupling.
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

  // TODOluke account for still motions
  // NON NULL vals are startTick, endTick, and shapeName
  // specify non null string in javadoc
  /**
   * Method to apply various modifications (motions) to previously declared shapes.
   *
   * @param startTick
   * @param endTick
   * @param shapeName
   * @param startX
   * @param startY
   * @param endX
   * @param endY
   * @param startWidth
   * @param startHeight
   * @param endWidth
   * @param endHeight
   * @param red
   * @param green
   * @param blue
   * @param redEnd
   * @param greenEnd
   * @param blueEnd
   * @throws IllegalArgumentException if the provided String is null or already in use.
   * @throws IllegalArgumentException if the type is unhandled or null.
   */
  void applyMotion(int startTick, int endTick, String shapeName,
                   Integer startX, Integer startY, Integer endX, Integer endY,
                   Integer startWidth, Integer startHeight, Integer endWidth, Integer endHeight,
                   Integer red, Integer green, Integer blue, Integer redEnd, Integer greenEnd,
                   Integer blueEnd);
}
