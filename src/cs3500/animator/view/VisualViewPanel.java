package cs3500.animator.view;

import cs3500.excellence.*;
import cs3500.excellence.Shape;

import java.awt.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class to represent a custom JPanel view component.
 */
public final class VisualViewPanel extends JPanel implements ChangeListener {
  private AnimationDelegate<Shape, Motion> delegate;
  private int ticks;
  private HashMap<String, Shape> state;
  private boolean shouldLoop;
  private JSlider frameSlider;
  private boolean shouldSlide;
  private boolean sliderInteraction;


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
    this.shouldSlide = false;
    this.sliderInteraction = false;
    this.setBackground(Color.WHITE);
    this.delegate = delegate;
    this.ticks = 1;
    state = delegate.retrieveCurrentGameState();
  }

  protected void scrubbaLubbaDubDub() {
      Integer maxTick = this.getLastTick();
      this.frameSlider = new JSlider(JSlider.HORIZONTAL,
          0, maxTick, 0);
      Bounds scrubberBounds = delegate.retrieveCanvasBoundaries();
      frameSlider.setPreferredSize(new Dimension((int) (scrubberBounds.width * 0.9), 15));
      this.add(frameSlider);
      frameSlider.addChangeListener(this);
      frameSlider.setVisible(false);
  }

  /**
   * CHANGE:
   * Now we get an updated version of the state at each tick. To ensure our visual is in alignment
   * with the model's updated state.
   */
  protected void updateTick() {
    this.state = delegate.retrieveCurrentGameState();
    if (shouldLoop && !sliderInteraction && this.ticks >= this.getLastTick()) {
      this.resetTick();
      if (frameSlider != null) {
        this.frameSlider.setValue(this.ticks);
      }
    } else if (!sliderInteraction) {
      this.ticks += 1;
      if (frameSlider != null) {
        this.frameSlider.setValue(this.ticks);
      }
    }
  }

  protected void resetTick() {
    this.ticks = 0;
    if (frameSlider != null) {
      this.frameSlider.setValue(this.ticks);
    }
  }

  protected void shouldLoop(boolean shouldLoop) {
    this.shouldLoop = shouldLoop;
    if (shouldLoop && this.ticks >= this.getLastTick()) {
      this.resetTick();
    }
  }

  protected int getLastTick() {
    int acc = 0;
    for (String each : this.state.keySet()) {
      ArrayList<Motion> currMotions = delegate.retrieveMotionsForObjectWithName(each);
      for (Motion every : currMotions) {
        if (every.endComp.tick >= acc) {
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
    ArrayList<String> orderedNames = new ArrayList<String>();
    ArrayList<ArrayList<String>> orderedLayers = this.delegate.retrieveOrderedLayers();
    for (ArrayList<String> layer: orderedLayers) {
      if (!layer.isEmpty()) {
        for (String name: layer) {
          orderedNames.add(name);
        }
      }
    }
    this.state = this.delegate.retrieveCurrentGameState();
    for (String name : orderedNames) {
      ShapeType renderType = this.state.get(name).type;
      ArrayList<Motion> shapeMovements = this.delegate.retrieveMotionsForObjectWithName(name);
      ArrayList<Rotation> retrievedRotations =
          this.delegate.retrieveRotationsForObjectWithName(name);
      int x = this.delegate.retrieveCanvasBoundaries().x;
      int y = this.delegate.retrieveCanvasBoundaries().y;
      for (Motion each : shapeMovements) {
        if (this.ticks >= each.startTick && this.ticks <= each.endTick) {
          Rotation currRotation = null;
          for (Rotation every : retrievedRotations) {
            if (this.ticks >= every.startTick) {
              currRotation = every;
              break;
            }
          }
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
              if (currRotation != null) {
                int rotation = ViewUtils.tweener(this.ticks, currRotation.startRadian,
                    currRotation.endRadian, currRotation.startTick, currRotation.endTick);
                AffineTransform transform = new AffineTransform();
                transform.rotate(Math.toRadians(rotation), curX + curW / 2,
                    curY + curH / 2);
                java.awt.Shape transformed = transform.createTransformedShape(e);
                g2d.fill(transformed);
              }
              else {
                g2d.fill(e);
              }
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
              if (currRotation != null) {
                int rectRotation = ViewUtils.tweener(this.ticks, currRotation.startRadian,
                    currRotation.endRadian, currRotation.startTick, currRotation.endTick);
                AffineTransform transform = new AffineTransform();
                transform.rotate(Math.toRadians(rectRotation), rcurX + rcurW / 2,
                    rcurY + rcurH / 2);
                java.awt.Shape transformed = transform.createTransformedShape(r);
                g2d.fill(transformed);
              }
              else {
                g2d.fill(r);
              }
              break;
            default:
              break;
          }
        }
      }
    }
  }

  protected void shouldSlide(boolean slideHuh) {
    this.shouldSlide = slideHuh;
    if (!this.shouldSlide) {
      this.frameSlider.setVisible(false);
    }
    else {
      this.frameSlider.setVisible(true);
    }
  }

  /**
   * Invoked when the target of the listener has changed its state.
   *
   * @param e a ChangeEvent object
   */
  @Override
  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider)e.getSource();
    if (!source.getValueIsAdjusting()) {
      int fps = (int)frameSlider.getValue();
      this.ticks = fps;
      this.sliderInteraction = false;
      this.repaint();
    }
    else if (source.getValueIsAdjusting()) {
      int fps = (int)frameSlider.getValue();
      this.ticks = fps;
      this.sliderInteraction = true;
      this.repaint();
    }
  }
}