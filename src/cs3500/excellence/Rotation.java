package cs3500.excellence;

/**
 * This class represent the change in orientation of a shape in radians from its center.
 */
public class Rotation extends UserInteraction {
  public final String associatedShape;
  public final int startTick;
  public final int endTick;
  public final Integer startRadian;
  public final Integer endRadian;

  /**
   * Constructor for a Rotation.
   *
   * @param associatedShape the shape being rotated.
   * @param startTick beginning of the rotation.
   * @param endTick end of the rotation.
   * @param startRadian starting radian.
   * @param endRadian ending radian
   */
  public Rotation(String associatedShape, int startTick, int endTick, Integer startRadian,
                  Integer endRadian) {
    this.associatedShape = associatedShape;
    this.startTick = startTick;
    this.endTick = endTick;
    this.startRadian = startRadian;
    this.endRadian = endRadian;
  }


  /**
   * Method for deriving String representations of objects that are user-facing.
   */
  @Override
  public String userMove() {
    return null;
  }
}
