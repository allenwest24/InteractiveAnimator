package cs3500.excellence;

/**
 * Immutable value object representing an RGB color.
 */
public class Color {

  public final int red;
  public final int green;
  public final int blue;

  /**
   * Constructor for a color object.
   */
  Color(int red, int green, int blue) {
    if (red <= 255 && red >= 0 && green <= 255 && green >= 0 && blue <= 255 && blue >= 0) {
      this.red = red;
      this.green = green;
      this.blue = blue;
    } else {
      throw new IllegalStateException("Invalid parameters.");
    }
  }

  protected boolean deriveEquivalency(Color other) {
    return this.blue == other.blue && this.green == other.green && this.red == other.red;
  }
}
