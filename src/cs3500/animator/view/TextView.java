package cs3500.animator.view;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

public class TextView implements IView {

  AnimationDelegate<Shape, Motion> delegate;

  public TextView(AnimationDelegate<Shape, Motion> delegate) {
    this.delegate = delegate;
  }

  @Override
  public String stringOutputForFile() {
    return delegate.getStringAnimation();
  }

  @Override
  public void refresh() {

  }

  @Override
  public void makeVisible() {

  }

}
