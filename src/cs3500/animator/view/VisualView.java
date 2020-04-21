package cs3500.animator.view;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.animator.controller.VCDelegate;
import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Bounds;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

/**
 * Concrete implementation of a visual "IView" with the mandatory functionality demanded by that
 * interface.
 */
public final class VisualView extends JFrame implements IView, ActionListener {
  private VisualViewPanel panel;

  /**
   * Public constructor for this object.
   *
   * @param delegate model with "read only" access to its states.
   * @param speed    indicates the desired speed for the animation to be played.
   */
  public VisualView(AnimationDelegate<Shape, Motion> delegate, int speed) {
    super();
    Timer timer = new Timer(1000 / speed, this::actionPerformed);
    this.setTitle("Animator");
    Bounds canvasBounds = delegate.retrieveCanvasBoundaries();
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());
    panel = new VisualViewPanel(delegate, canvasBounds.x, canvasBounds.y);
    panel.setPreferredSize(new Dimension(1000, 1000));
    this.setVisible(true);
    this.add(panel);
    timer.start();
  }

  /**
   * Get the String version of the output for the specific type of view.
   *
   * @return the String representing the view or an empty String if the view is visual.
   * @throws UnsupportedOperationException for views that do not have a String output.
   */
  @Override
  public String stringOutputForFile() {
    throw new UnsupportedOperationException("Can't do that!");
  }

  /**
   * Refresh the view to reflect any changes in the animator state.
   *
   * @throws UnsupportedOperationException for views that do not have a Visual.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * Make the view visible to start the animator if this view has a visible component.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Accept a ViewController that owns this View.
   *
   * @param vcd the delegate that owns this view.
   * @throws UnsupportedOperationException for views that do not have an owner.
   */
  @Override
  public void acceptViewController(VCDelegate vcd) {
    throw new UnsupportedOperationException("Can't do that!");
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    this.panel.updateTick();
    this.refresh();
  }
}
