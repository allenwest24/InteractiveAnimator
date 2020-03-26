package cs3500.animator.view;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

/**
 * Concrete implementation of a textual "IView" with the mandatory functionality demanded by that
 * interface.
 */
public class TextView implements IView {

  AnimationDelegate<Shape, Motion> delegate;

  /**
   * Public constructor for this object.
   *
   * @param delegate model with "read only" access to its states.
   */
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
