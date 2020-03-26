package cs3500.animator.view;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

public class SVGView implements IView {

  AnimationDelegate<Shape, Motion> delegate;

  public SVGView(AnimationDelegate<Shape, Motion> delegate) {
    this.delegate = delegate;
  }

  @Override
  public String stringOutputForFile() {
    return null;
  }

  @Override
  public void refresh() {

  }

  @Override
  public void makeVisible() {

  }
}
