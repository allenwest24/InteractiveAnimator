package cs3500.excellence;

/**
 * A value-type class for representing the bounds associated with an animator.
 */
public class Bounds extends UserInteraction {

  public final int x;
  public final int y;
  public final int width;
  public final int height;

  Bounds(int x, int y, int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Negative parameters");
    }
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  @Override
  public String userMove() {
    return "canvas " + this.x + " " + this.y + " " + this.width + " " + this.height;
  }
}