package cs3500.animator.view;

import cs3500.animator.controller.VCDelegate;

/**
 * A view for our Animator: display the listed animations to provide the visual component to the
 * animator for the viewer.
 */
public interface IView {

  /**
   * Get the String version of the output for the specific type of view.
   *
   * @return the String representing the view or an empty String if the view is visual.
   * @throws UnsupportedOperationException for views that do not have a String output.
   */
  String stringOutputForFile();

  /**
   * Refresh the view to reflect any changes in the animator state.
   *
   * @throws UnsupportedOperationException for views that do not have a Visual.
   */
  void refresh();

  /**
   * Make the view visible to start the animator if this view has a visible component.
   */
  void makeVisible();

  /**
   * Accept a ViewController that owns this View.
   *
   * @param vcd the delegate that owns this view.
   * @throws UnsupportedOperationException for views that do not have an owner.
   */
  void acceptViewController(VCDelegate vcd);
}
