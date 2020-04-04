package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Bounds;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;

/**
 * Concrete implementation of a edit-able "IView" with the mandatory functionality demanded by that
 * interface.
 */
public final class EditorView extends JFrame implements IView, ActionListener, ViewDelegate {

  private AnimationDelegate<Shape, Motion> delegate;
  private VisualViewPanel panel;
  private int speed;
  private Timer timer;
  private DropDownPanel settings;
  private JMenuBar bar;
  private boolean currentlyPaused;
  private boolean loopHuh;

  /**
   * Public constructor for this object.
   *
   * @param delegate model with "read only" access to its states.
   * @param speed    indicates the desired speed for the animation to be played.
   */
  public EditorView(AnimationDelegate<Shape, Motion> delegate, int speed) {
    super();
    this.loopHuh = false;
    this.currentlyPaused = false;
    this.speed = speed;
    this.delegate = delegate;
    this.timer = new Timer(1000 / speed, this::actionPerformed);
    this.setTitle("Animator");
    Bounds canvasBounds = delegate.retrieveCanvasBoundaries();
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());
    this.bar = new JMenuBar();
    this.settings = new DropDownPanel(this);
    this.bar.add(this.settings);
    this.setJMenuBar(this.bar);
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
    if(!currentlyPaused) {
      this.panel.updateTick();
      this.refresh();
    }
  }

  @Override
  public void play() {
    this.currentlyPaused = false;
  }

  @Override
  public void pauseAnimation() {
    this.currentlyPaused = true;
  }

  @Override
  public void resetAnimation() {
    this.panel.resetTick();
    this.refresh();
  }

  @Override
  public void loopAnimation() {
    this.loopHuh = !this.loopHuh;
    this.panel.shouldLoop(this.loopHuh);
  }

  @Override
  public void setSpeed(int speed) {

  }
}
