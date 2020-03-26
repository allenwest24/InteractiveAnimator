package cs3500.animator.view;

import javax.swing.*;

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
public class VisualViewPanel extends JPanel {

  AnimationDelegate<Shape, Motion> delegate;
  int xOffset;
  int yOffset;
  int speed;
  int ticks;
  HashMap<String, Shape> state;

  //todoluke, tomorrow, when we have a sexy game, consider null check.
  /**
   * Public constructor for this object.
   *
   * @param delegate the non-null ReadOnly model that is perpetuated down from the view.
   * @param xOrigin the desired x-coordinates for the upper left corner of the panel (the x offset).
   * @param yOrigin the desired y-coordinates for the upper left corner of the panel (the y offset).
   */
  VisualViewPanel(AnimationDelegate<Shape, Motion> delegate, int xOrigin, int yOrigin) {
    super();
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
    return (int) Math.round(a * ((time2 - time) / (time2 - time1)) + b * ((time - time1) / (time2 - time1)));
  }

  protected void updateTick() {
    this.ticks += 1;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    Ellipse2D e1 = new Ellipse2D.Double(440, 70, 10, 10);
    g2d.setColor(Color.BLACK);
    g2d.fill(e1);
    for (String name : this.state.keySet()) {
      ShapeType renderType = this.state.get(name).type;
      ArrayList<Motion> shapeMovements = this.delegate.retrieveMotionsForObjectWithName(name);
      int x = this.delegate.retrieveCanvasBoundaries().x;
      int y = this.delegate.retrieveCanvasBoundaries().y;
      for (Motion each : shapeMovements) {
        if (this.ticks >= each.startTick && this.ticks <= each.endTick) {
          switch (renderType) {
            case ELLIPSE:
              int curX = this.tweener(this.ticks, each.startComp.x, each.endComp.x, each.startTick, each.endTick);
              int curY = this.tweener(this.ticks, each.startComp.y, each.endComp.y, each.startTick, each.endTick);
              int curW = this.tweener(this.ticks, each.startComp.width, each.endComp.width, each.startTick, each.endTick);
              int curH = this.tweener(this.ticks, each.startComp.height, each.endComp.height, each.startTick, each.endTick);

              int curR = this.tweener(this.ticks, each.startComp.color.red, each.endComp.color.red, each.startTick, each.endTick);
              int curB = this.tweener(this.ticks, each.startComp.color.blue, each.endComp.color.blue, each.startTick, each.endTick);
              int curG = this.tweener(this.ticks, each.startComp.color.green, each.endComp.color.green, each.startTick, each.endTick);

              System.out.println("tweened. x: " + curX + ", y: " + curY + ", w: " + curW + ", h: " + curH);
              Ellipse2D e = new Ellipse2D.Double(curX + x, curY + y, curW, curH);
              g2d.setColor(new Color(curR, curB, curG));
              g2d.fill(e);
              break;
            case RECTANGLE:
              int rcurX = this.tweener(this.ticks, each.startComp.x, each.endComp.x, each.startTick, each.endTick);
              int rcurY = this.tweener(this.ticks, each.startComp.y, each.endComp.y, each.startTick, each.endTick);
              int rcurW = this.tweener(this.ticks, each.startComp.width, each.endComp.width, each.startTick, each.endTick);
              int rcurH = this.tweener(this.ticks, each.startComp.height, each.endComp.height, each.startTick, each.endTick);

              int rcurR = this.tweener(this.ticks, each.startComp.color.red, each.endComp.color.red, each.startTick, each.endTick);
              int rcurB = this.tweener(this.ticks, each.startComp.color.blue, each.endComp.color.blue, each.startTick, each.endTick);
              int rcurG = this.tweener(this.ticks, each.startComp.color.green, each.endComp.color.green, each.startTick, each.endTick);

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
//    g2d.drawLine(0, 100, 300, 100);
//    g2d.drawLine(0, 200, 300, 200);
//    g2d.drawLine(0, 300, 300, 300);
//    g2d.drawLine(100, 0, 100, 300);
//    g2d.drawLine(200, 0, 200, 300);
//    g2d.drawString("Player " + model.getTurn().toString() + "'s turn.", 100, 350);
//    String offset = "             ";
//    String gameOver = model.isGameOver() ? offset + "Game is over." : "  Game currently happening!";
//    g2d.drawString(gameOver, 50, 400);
//    Player[][] board = model.getBoard();
//    int jOffSet = 0;
//    int iOffset = 0;
//    for (int i = 0; i < 3; i++) {
//      jOffSet = 0;
//      for (int j = 0; j < 3; j++) {
//        Player occupant = board[i][j];
//        if (occupant != null) {
//          g2d.drawString(occupant.toString(), i + 50 + iOffset, j + 50 + jOffSet);
//        }
//        jOffSet += 100;
//      }
//      iOffset += 100;
//    }
  }
}

///**
// * Class to represent a custom JPanel view component.
// */
//public final class TTTPanel extends JPanel {
//
//  private ReadonlyTTTModel model;
//
//  /**
//   * Public constructor for this object.
//   *
//   * @param model the non-null ReadOnly model that is perpetuated down from the view.
//   * @throws IllegalArgumentException for a null parameter.
//   */
//  public TTTPanel(ReadonlyTTTModel model) {
//    super();
//    if (model == null) {
//      throw new IllegalArgumentException("Can't instantiate with a null parameter.");
//    }
//    this.model = model;
//    this.setBackground(Color.WHITE);
//  }
//
//  @Override
//  protected void paintComponent(Graphics g) {
//    super.paintComponent(g);
//
//    Graphics2D g2d = (Graphics2D) g;
//
//    g2d.setColor(Color.BLACK);
//    g2d.drawLine(0, 100, 300, 100);
//    g2d.drawLine(0, 200, 300, 200);
//    g2d.drawLine(0, 300, 300, 300);
//    g2d.drawLine(100, 0, 100, 300);
//    g2d.drawLine(200, 0, 200, 300);
//    g2d.drawString("Player " + model.getTurn().toString() + "'s turn.", 100, 350);
//    String offset = "             ";
//    String gameOver = model.isGameOver() ? offset + "Game is over." : "  Game currently happening!";
//    g2d.drawString(gameOver, 50, 400);
//    Player[][] board = model.getBoard();
//    int jOffSet = 0;
//    int iOffset = 0;
//    for (int i = 0; i < 3; i++) {
//      jOffSet = 0;
//      for (int j = 0; j < 3; j++) {
//        Player occupant = board[i][j];
//        if (occupant != null) {
//          g2d.drawString(occupant.toString(), i + 50 + iOffset, j + 50 + jOffSet);
//        }
//        jOffSet += 100;
//      }
//      iOffset += 100;
//    }
//  }
//}
