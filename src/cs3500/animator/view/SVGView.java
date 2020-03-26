package cs3500.animator.view;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

/**
 * Concrete implementation of a svg "IView" with the mandatory functionality demanded by that
 * interface.
 */
public class SVGView implements IView {

  AnimationDelegate<Shape, Motion> delegate;

  /**
   * Public constructor for this object.
   *
   * @param delegate model with "read only" access to its states.
   */
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
