package cs3500.animator.view;

import javax.swing.*;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.Motion;
import cs3500.excellence.Shape;
import cs3500.excellence.ShapeType;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;


public class VisualViewPanel extends JPanel {

  AnimationDelegate<Shape, Motion> delegate;
  int xOffset;
  int yOffset;
  int speed;
  int ticks;
  HashMap<String, Shape> state;

  //todoluke, tomorrow, when we have a sexy game, consider null check.
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
  private int tweener(int a, int b, int t1, int t2) {
    return (a * ((t2 - this.ticks) / (t2 - t1))) + (b * ((this.ticks - t1) / (t2 - t1)));
  }

  protected void updateTick() {
    this.ticks += 1;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);
    for (String name : this.state.keySet()) {
      ShapeType renderType = this.state.get(name).type;
      ArrayList<Motion> shapeMovements = this.delegate.retrieveMotionsForObjectWithName(name);
      for (Motion each : shapeMovements) {
        if (each.startTick >= this.ticks && each.endTick <= this.ticks) {
          switch (renderType) {
            case ELLIPSE:
              int curX = this.tweener(each.startComp.x, each.endComp.x,
                      each.startTick, each.endTick);
              int curY = this.tweener(each.startComp.y, each.endComp.y,
                      each.startTick, each.endTick);
              int curW = this.tweener(each.startComp.width, each.endComp.width,
                    each.startTick, each.endTick);
              int curH = this.tweener(each.startComp.height, each.endComp.height,
                      each.startTick, each.endTick);

              break;
            case RECTANGLE:
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
