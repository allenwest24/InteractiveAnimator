package cs3500.excellence;

public class Color {

  protected final int red;
  protected final int green;
  protected final int blue;

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
