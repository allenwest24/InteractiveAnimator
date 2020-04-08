package cs3500.animator.view;

import cs3500.animator.controller.VCDelegate;
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

  /**
   * Get the String version of the output for the specific type of view.
   *
   * @return the String representing the view or an empty String if the view is visual.
   * @throws UnsupportedOperationException for views that do not have a String output.
   */
  @Override
  public String stringOutputForFile() {
    Bounds bounds = this.delegate.retrieveCanvasBoundaries();
    return bounds.userMove() + "\n" + delegate.getStringAnimation();
  }

  /**
   * Refresh the view to reflect any changes in the animator state.
   *
   * @throws UnsupportedOperationException for views that do not have a Visual.
   */
  @Override
  public void refresh() {
    throw new UnsupportedOperationException("This view does not have a Visual.");
  }

  /**
   * Make the view visible to start the animator.
   *
   * @throws UnsupportedOperationException for views that do not have a Visual.
   */
  @Override
  public void makeVisible() {
    return;
  }

  /**
   * Accept a ViewController that owns this View.
   *
   * @param vcd the delegate that owns this view.
   * @throws UnsupportedOperationException for views that do not have an owner.
   */
  @Override
  public void acceptViewController(VCDelegate vcd) {
    throw new UnsupportedOperationException("This view does not have an owner.");
  }
}
