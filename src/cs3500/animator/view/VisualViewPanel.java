package cs3500.animator.view;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;
import cs3500.excellence.ShapeType;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

/**
 * Class to represent a custom JPanel view component.
 */
public final class VisualViewPanel extends JPanel {

  private AnimationDelegate<Shape, Motion> delegate;
  private int xOffset;
  private int yOffset;
  private int speed;
  private int ticks;
  private HashMap<String, Shape> state;
  private boolean shouldLoop;

  /**
   * Public constructor for this object.
   *
   * @param delegate the non-null ReadOnly model that is perpetuated down from the view.
   * @param xOrigin  the desired x-coordinates for the upper left corner of the panel (the x
   *                 offset).
   * @param yOrigin  the desired y-coordinates for the upper left corner of the panel (the y
   *                 offset).
   */
  VisualViewPanel(AnimationDelegate<Shape, Motion> delegate, int xOrigin, int yOrigin) {
    super();
    this.shouldLoop = false;
    this.setBackground(Color.WHITE);
    this.delegate = delegate;
    this.xOffset = xOrigin;
    this.yOffset = yOrigin;
    this.speed = 1;
    this.ticks = 1;
    state = delegate.retrieveCurrentGameState();
  }

  /**
   * CHANGE:
   * Now we get an updated version of the state at each tick. To ensure our visual is in alignment
   * with the model's updated state.
   */
  protected void updateTick() {
    this.state = delegate.retrieveCurrentGameState();
    if (shouldLoop && this.ticks == this.getLastTick()) {
      this.resetTick();
    }
    else {
      this.ticks += 1;
    }
  }

  protected void resetTick() {
    this.ticks = 0;
  }

  protected void shouldLoop(boolean shouldLoop) {
    this.shouldLoop = shouldLoop;
    if(shouldLoop && this.ticks >= this.getLastTick()) {
      this.resetTick();
    }
  }

  private int getLastTick() {
    int acc = 0;
    for(String each: this.state.keySet()) {
      ArrayList<Motion> currMotions = delegate.retrieveMotionsForObjectWithName(each);
      for(Motion every: currMotions) {
        if(every.endComp.tick >= acc) {
          acc = every.endComp.tick;
        }
      }
    }
    return acc;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    for (String name : this.state.keySet()) {
      ShapeType renderType = this.state.get(name).type;
      ArrayList<Motion> shapeMovements = this.delegate.retrieveMotionsForObjectWithName(name);
      int x = this.delegate.retrieveCanvasBoundaries().x;
      int y = this.delegate.retrieveCanvasBoundaries().y;
      for (Motion each : shapeMovements) {
        if (this.ticks >= each.startTick && this.ticks <= each.endTick) {
          switch (renderType) {
            case ELLIPSE:
              int curX = ViewUtils.tweener(this.ticks, each.startComp.x,
                      each.endComp.x, each.startTick, each.endTick);
              int curY = ViewUtils.tweener(this.ticks, each.startComp.y,
                      each.endComp.y, each.startTick, each.endTick);
              int curW = ViewUtils.tweener(this.ticks, each.startComp.width,
                      each.endComp.width, each.startTick, each.endTick);
              int curH = ViewUtils.tweener(this.ticks, each.startComp.height,
                      each.endComp.height, each.startTick, each.endTick);
              int curR = ViewUtils.tweener(this.ticks, each.startComp.color.red,
                      each.endComp.color.red, each.startTick, each.endTick);
              int curB = ViewUtils.tweener(this.ticks, each.startComp.color.blue,
                      each.endComp.color.blue, each.startTick, each.endTick);
              int curG = ViewUtils.tweener(this.ticks, each.startComp.color.green,
                      each.endComp.color.green, each.startTick, each.endTick);
              Ellipse2D e = new Ellipse2D.Double(curX + x, curY + y, curW, curH);
              g2d.setColor(new Color(curR, curG, curB));
              g2d.fill(e);
              break;
            case RECTANGLE:
              int rcurX = ViewUtils.tweener(this.ticks, each.startComp.x,
                      each.endComp.x, each.startTick, each.endTick);
              int rcurY = ViewUtils.tweener(this.ticks, each.startComp.y,
                      each.endComp.y, each.startTick, each.endTick);
              int rcurW = ViewUtils.tweener(this.ticks, each.startComp.width,
                      each.endComp.width, each.startTick, each.endTick);
              int rcurH = ViewUtils.tweener(this.ticks, each.startComp.height,
                      each.endComp.height, each.startTick, each.endTick);
              int rcurR = ViewUtils.tweener(this.ticks, each.startComp.color.red,
                      each.endComp.color.red, each.startTick, each.endTick);
              int rcurB = ViewUtils.tweener(this.ticks, each.startComp.color.blue,
                      each.endComp.color.blue, each.startTick, each.endTick);
              int rcurG = ViewUtils.tweener(this.ticks, each.startComp.color.green,
                      each.endComp.color.green, each.startTick, each.endTick);
              Rectangle2D r = new Rectangle2D.Double(rcurX + x, rcurY + y, rcurW, rcurH);
              g2d.setColor(new Color(rcurR, rcurG, rcurB));
              g2d.fill(r);
              break;
            default:
              break;
          }
        }
      }
    }
  }
}