package cs3500.animator.view;

public interface ViewDelegate {
  void play();

  void pauseAnimation();

  // Can be null to reset to beginning.
  void resetAnimation();

  void loopAnimation();

  void setSpeed(int speed);
}
