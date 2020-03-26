package cs3500.excellence;

public class Bounds extends UserInteraction {

  public final int x;
  public final int y;
  public final int width;
  public final int height;

  Bounds(int x, int y, int width, int height) {
    if (x < 0 || y < 0 || width < 0 || height < 0) {
      throw new IllegalArgumentException("Negative parameters");
    }
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  //canvas 200 70 360 360
  @Override
  protected String userMove() {
    return "canvas " + this.x + " " + this.y + " " + this.width + " " + this.height;
  }
}