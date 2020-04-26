package cs3500.animator.view;

/**
 * Utils class to provide static 'tweener' functionality to all views.
 */
public class ViewUtils {

  /**
   * Static 'tweener' function to calculate the values in between keyframes.
   *
   * @param time  the tick to calculate.
   * @param a     a parameter to the tweener function.
   * @param b     a parameter to the tweener function.
   * @param time1 a parameter to the tweener function.
   * @param time2 a parameter to the tweener function.
   * @return the 'tweened' and rounded int value.
   */
  public static int tweener(double time, int a, int b, int time1, int time2) {
    return (int)
        Math.round(a * ((time2 - time)
            / (time2 - time1)) + b * ((time - time1)
            / (time2 - time1)));
  }
}
