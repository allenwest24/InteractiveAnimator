package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

import javax.swing.*;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Bounds;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

// TODOluke revise all access modifiers and make a bunch of shit final
/**
 * Concrete implementation of a visual "IView" with the mandatory functionality demanded by that
 * interface.
 */
public class VisualView extends JFrame implements IView, ActionListener {

  private AnimationDelegate<Shape, Motion> delegate;
  private VisualViewPanel panel;
  private int speed;
  private Timer timer;

  /**
   * Public constructor for this object.
   *
   * @param delegate model with "read only" access to its states.
   * @param speed indicates the desired speed for the animation to be played.
   */
  public VisualView(AnimationDelegate<Shape, Motion> delegate, int speed) {
    super();
    this.speed = speed;
    this.delegate = delegate;
    this.timer = new Timer(1000 / speed, this::actionPerformed);
    this.setTitle("Animator");
    Bounds canvasBounds = delegate.retrieveCanvasBoundaries();
    // todo luke fix this shit mothafucka
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());
    panel = new VisualViewPanel(delegate, canvasBounds.x, canvasBounds.y);
    panel.setPreferredSize(new Dimension(1000, 1000));
    this.setVisible(true);
    this.add(panel);
    this.timer.start();
  }

  @Override
  public String stringOutputForFile() {
    return "";
  }

    /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
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
