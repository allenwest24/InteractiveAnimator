package cs3500.animator.view;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

public class VisualView implements IView {

  private AnimationDelegate<Shape, Motion> delegate;

  @Override
  public String stringOutputForFile() {
    return null;
  }

  @Override
  public void displayVisuallyIfPossible() {

  }

  @Override
  public void adjustSpeed(int newSpeed) {

  }

  @Override
  public void acceptDelegate(AnimationDelegate<Shape, Motion> delegator) {

  }
}
