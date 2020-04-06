package cs3500.animator.view;

public class ViewUtils {
  //𝑓(𝑡)=𝑎(𝑡𝑏−𝑡𝑡𝑏−𝑡𝑎)+𝑏(𝑡−𝑡𝑎𝑡𝑏−𝑡𝑎)
  public static int tweener(double time, int a, int b, int time1, int time2) {
    return (int)
        Math.round(a * ((time2 - time)
            / (time2 - time1)) + b * ((time - time1)
            / (time2 - time1)));
  }
}
