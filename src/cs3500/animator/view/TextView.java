package cs3500.animator.view;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Bounds;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

/**
 * Concrete implementation of a textual "IView" with the mandatory functionality demanded by that
 * interface.
 */
public final class TextView implements IView {

  private AnimationDelegate<Shape, Motion> delegate;

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
    Bounds bounds = this.delegate.retrieveCanvasBoundaries();
    return bounds.userMove() + "\n" + delegate.getStringAnimation();
  }

  @Override
  public void refresh() {
    return;
  }

  @Override
  public void makeVisible() {
    return;
  }

}
