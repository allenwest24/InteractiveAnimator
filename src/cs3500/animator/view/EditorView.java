package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.animator.controller.VCDelegate;
import cs3500.excellence.*;
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
  private VCDelegate<ShapeType> vcd;

  /**
   * Public constructor for this object.
   *
   * @param delegate model with "read only" access to its states.
   * @param speed    indicates the desired speed for the animation to be played.
   * @throws IllegalArgumentException for null params.
   */
  public EditorView(AnimationDelegate<Shape, Motion> delegate, int speed) {
    super();
    if (delegate == null) {
      throw new IllegalArgumentException("Cannot accept null parameters.");
    }
    this.loopHuh = false;
    this.currentlyPaused = false;
    this.speed = speed;
    this.delegate = delegate;
    this.timer = new Timer(1000 / this.speed, this::actionPerformed);
    this.setTitle("Animator");
    Bounds canvasBounds = delegate.retrieveCanvasBoundaries();
    this.setSize(canvasBounds.width, canvasBounds.height);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());
    this.bar = new JMenuBar();
    this.settings = new DropDownPanel(this, this);
    this.bar.add(this.settings);
    this.setJMenuBar(this.bar);
    panel = new VisualViewPanel(delegate, canvasBounds.x, canvasBounds.y);
    panel.setPreferredSize(new Dimension(canvasBounds.width, canvasBounds.height));
    this.setVisible(true);
    this.add(panel);
    this.timer.start();
  }

  /**
   * Get the String version of the output for the specific type of view.
   *
   * @return the String representing the view or an empty String if the view is visual.
   * @throws UnsupportedOperationException for views that do not have a String output.
   */
  @Override
  public String stringOutputForFile() {
    throw new UnsupportedOperationException("This view does not have a String output.");
  }

  /**
   * Refresh the view to reflect any changes in the game state.
   *
   * @throws UnsupportedOperationException for views that do not have an owner.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * Make the view visible to start the animation session.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Accept a ViewController that owns this View.
   *
   * @throws UnsupportedOperationException for views that do not have an owner.
   */
  @Override
  public void acceptViewController(VCDelegate vcd) {
    this.vcd = vcd;
  }


  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (!currentlyPaused) {
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
  public void setSpeed(int newSpeed) {
    this.speed = newSpeed;
    this.timer.stop();
    this.timer = new Timer(1000 / this.speed, this::actionPerformed);
    this.timer.start();
  }

  @Override
  public boolean doesShapeExistForName(String name) {
    try {
      delegate.retrieveMotionsForObjectWithName(name);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  @Override
  public void userRequestsDeleteShape(String s) {
    this.vcd.userRequestsDeleteShape(s);
  }

  @Override
  public void userRequestsAddShape(ShapeType type, String name) {
    this.vcd.userRequestsAddShape(type, name);
  }

  @Override
  public boolean deleteKeyFrame(String shapeName, Integer i) {
    return this.vcd.userRequestsDeleteKeyFrame(shapeName, i);
  }

  @Override
  public String canAddKeyFrameAtTick(String shapeName, Integer i) {
    String potentialDisplay = null;
    try {
      potentialDisplay = this.vcd.userRequestAddKeyFrameInfo(shapeName, i);
    } catch (IllegalArgumentException e) {
      return null;
    }
    return potentialDisplay;
  }

  @Override
  public boolean passNewValuesOnKeyFrame(boolean b, Integer tempx2, Integer tempy2, Integer tempw2,
                                         Integer temph2, Integer tempr2, Integer tempg2,
                                         Integer tempb2) {
    if (!b) {
      vcd.passNewValuesOnKeyFrameAgain(false, null, null, null,
          null, null, null, null);
      return b;
    }
    return vcd.passNewValuesOnKeyFrameAgain(true, tempx2, tempy2, tempw2, temph2,
        tempr2, tempg2, tempb2);
  }
}
