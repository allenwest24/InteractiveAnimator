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

  //𝑓(𝑡)=𝑎(𝑡𝑏−𝑡𝑡𝑏−𝑡𝑎)+𝑏(𝑡−𝑡𝑎𝑡𝑏−𝑡𝑎)
  private int tweener(double time, int a, int b, int time1, int time2) {
    return (int)
            Math.round(a * ((time2 - time)
                    / (time2 - time1)) + b * ((time - time1)
                    / (time2 - time1)));
  }

  protected void updateTick() {
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
              int curX = this.tweener(this.ticks, each.startComp.x,
                      each.endComp.x, each.startTick, each.endTick);
              int curY = this.tweener(this.ticks, each.startComp.y,
                      each.endComp.y, each.startTick, each.endTick);
              int curW = this.tweener(this.ticks, each.startComp.width,
                      each.endComp.width, each.startTick, each.endTick);
              int curH = this.tweener(this.ticks, each.startComp.height,
                      each.endComp.height, each.startTick, each.endTick);
              int curR = this.tweener(this.ticks, each.startComp.color.red,
                      each.endComp.color.red, each.startTick, each.endTick);
              int curB = this.tweener(this.ticks, each.startComp.color.blue,
                      each.endComp.color.blue, each.startTick, each.endTick);
              int curG = this.tweener(this.ticks, each.startComp.color.green,
                      each.endComp.color.green, each.startTick, each.endTick);
              Ellipse2D e = new Ellipse2D.Double(curX + x, curY + y, curW, curH);
              g2d.setColor(new Color(curR, curB, curG));
              g2d.fill(e);
              break;
            case RECTANGLE:
              int rcurX = this.tweener(this.ticks, each.startComp.x,
                      each.endComp.x, each.startTick, each.endTick);
              int rcurY = this.tweener(this.ticks, each.startComp.y,
                      each.endComp.y, each.startTick, each.endTick);
              int rcurW = this.tweener(this.ticks, each.startComp.width,
                      each.endComp.width, each.startTick, each.endTick);
              int rcurH = this.tweener(this.ticks, each.startComp.height,
                      each.endComp.height, each.startTick, each.endTick);
              int rcurR = this.tweener(this.ticks, each.startComp.color.red,
                      each.endComp.color.red, each.startTick, each.endTick);
              int rcurB = this.tweener(this.ticks, each.startComp.color.blue,
                      each.endComp.color.blue, each.startTick, each.endTick);
              int rcurG = this.tweener(this.ticks, each.startComp.color.green,
                      each.endComp.color.green, each.startTick, each.endTick);
              Rectangle2D r = new Rectangle2D.Double(rcurX + x, rcurY + y, rcurW, rcurH);
              g2d.setColor(new Color(rcurR, rcurB, rcurG));
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