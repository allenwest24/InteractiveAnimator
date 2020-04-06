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
   */
  String stringOutputForFile();

  /**
   * Refresh the view to reflect any changes in the animator state.
   */
  void refresh();

  /**
   * Make the view visible to start the animator.
   */
  void makeVisible();

  /**
   * Accept a ViewController that owns this View.
   */
  void acceptViewController(VCDelegate vcd);

}
