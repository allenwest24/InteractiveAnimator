package cs3500.animator.view;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

public interface IView {

  String stringOutputForFile();

  void displayVisuallyIfPossible();

  void adjustSpeed(int newSpeed);

  void acceptDelegate(AnimationDelegate<Shape, Motion> delegator);
}
