package cs3500.excellence;

/**
 * Abstract class that mandates string displayable state representation when implemented.
 */
abstract class UserInteraction {

  /**
   * Method for deriving String representations of objects that are user-facing.
   */
  public abstract String userMove();

  public boolean objectAssociatedWithName(String name) {
    return false;
  }

  /**
   * Changed: Added for filtering out removed KeyFrame strings.
   */
  public boolean motionAssociatedWithNameAndTick(String name, Integer tick) {
    return false;
  }
}
