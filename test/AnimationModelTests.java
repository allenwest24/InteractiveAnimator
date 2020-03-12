import org.junit.Test;

import java.util.ArrayList;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.AnimationModel;
import cs3500.excellence.IAnimation;
import cs3500.excellence.ShapeType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the IAnimation and AnimationDelegate interfaces, which contain the only public
 * methods in this project.
 */
public class AnimationModelTests {
  AnimationModel animation = new AnimationModel();


  @Test
  public void testGetterNoShapesOrMotions() {
    // First we limit access to model interface, pretending we are a delegate (read only)
    AnimationDelegate delegateReference = animation;
    assertTrue(delegateReference.getStringAnimation().equals(""));
  }

  @Test
  public void testAddingShapes() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    assertTrue(viewPerspective.getStringAnimation().equals("shape Oval ellipse"));
  }

  @Test
  public void testAddingShapesMulti() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "R");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    assertTrue(viewPerspective.getStringAnimation().equals("shape R ellipse"
            + "\n" + "shape Rect rectangle"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionForUndeclaredShape() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "HI", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionWithContradictoryTicks() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(3, 2, "HI", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
  }

  @Test
  public void testSuccessWithNegativeCoords() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", -13, -14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
    assertEquals(viewPerspective.retrieveMotionsForObjectWithName("Rect").size(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailureNegativeWidth() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", -13, -14,
            15, 15, -45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
    assertEquals(viewPerspective.retrieveMotionsForObjectWithName("Rect").size(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailureNegativeHeight() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", -13, -14,
            15, 15, 45, -23, 45, 23, 1, 2,
            3, 1, 2, 3);
    assertEquals(viewPerspective.retrieveMotionsForObjectWithName("Rect").size(), 1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testMotionForNullShapeName() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, null, 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeShapeType() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(null, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "null", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeForNullNameCreation() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.RECTANGLE, null);
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "null", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
  }

  @Test
  public void testValidMotionWithShape() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
    assertTrue(viewPerspective.getStringAnimation().equals("shape Oval ellipse"
            + "\n" + "shape Rect rectangle" + "\n"
            + "motion Rect 10 13 14 45 23 1 2 3 12 15 15 45 23 1 2 3"));
  }

  @Test
  public void testMotionPersisence() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
    controllerPerspective.applyMotion(2, 3, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
    assertEquals(viewPerspective.retrieveMotionsForObjectWithName("Rect").size(), 2);
  }

  @Test
  public void testValidMotionThenShape() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
    controllerPerspective.applyMotion(2, 3, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval2");
    assertEquals(viewPerspective.retrieveMotionsForObjectWithName("Rect").size(), 2);
  }

  @Test
  public void testValidMotionThenShapes() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
    controllerPerspective.applyMotion(2, 3, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval23");
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval223");
    assertEquals(viewPerspective.retrieveMotionsForObjectWithName("Rect").size(), 2);
  }

  @Test
  public void testJustWidth() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
    controllerPerspective.applyMotion(2, 3, "Rect", null, null,
            null, null, 45, null, 20, null, null,
            null,
            null, null, null, null);
    assertTrue(viewPerspective.getStringAnimation().equals("shape Oval ellipse"
            + "\n" + "shape Rect rectangle" + "\n"
            + "motion Rect 1 13 14 45 23 1 2 3 2 15 15 45 23 1 2 3" +
            "\n" + "motion Rect 2 15 15 45 23 1 2 3 3 15 15 20 23 1 2 3"));
  }

  @Test
  public void testMotionForDoingNothingAtAllStayingTheSame() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
    controllerPerspective.applyMotion(2, 3, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
    assertTrue(viewPerspective.getStringAnimation().equals("shape Oval ellipse"
            + "\n" + "shape Rect rectangle" + "\n"
            + "motion Rect 1 13 14 45 23 1 2 3 2 15 15 45 23 1 2 3" +
            "\n" + "motion Rect 2 15 15 45 23 1 2 3 3 15 15 45 23 1 2 3"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFaultyParameterFromViewToDelegate() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "HI", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 2, 3);
    ArrayList<?> expectedException = viewPerspective.retrieveMotionsForObjectWithName(null);
  }

  @Test
  public void testMotionJustChangingColor() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 3, 10);
    controllerPerspective.applyMotion(2, 3, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
    assertTrue(viewPerspective.getStringAnimation().equals("shape Oval ellipse"
            + "\n" + "shape Rect rectangle" + "\n"
            + "motion Rect 1 13 14 45 23 1 2 3 2 15 15 45 23 1 3 10" +
            "\n" + "motion Rect 2 15 15 45 23 1 3 10 3 15 15 45 23 1 3 10"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTickGapException() {
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1, 2, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 3, 10);
    controllerPerspective.applyMotion(18, 3, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTickTeleportationFailure() {
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 3, 10);
    controllerPerspective.applyMotion(1, 3, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionException1() {
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, 1, 2,
            3, 1, 3, 10);
    controllerPerspective.applyMotion(12, -3, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionException2() {
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", 13, 14,
            15, 15, 45, 23, 45, 23, null, 2,
            3, 1, 3, 10);
    controllerPerspective.applyMotion(12, 13, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionException3() {
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", 13, 14,
            15, 15, null, 23, 45, 23, 1, 2,
            3, 1, 3, 10);
    controllerPerspective.applyMotion(12, 13, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionException4() {
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", 13, 14,
            15, 15, 45, null, 45, 23, 1, 2,
            3, 1, 3, 10);
    controllerPerspective.applyMotion(12, 13, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionException5() {
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(10, 12, "Rect", 13, 14,
            15, 15, 45, null, 45, 23, 1, 2,
            3, 1, 3, 10);
    controllerPerspective.applyMotion(12, 13, "Rect", null, null,
            null, null, null, null, null, null, null,
            null,
            null, null, null, null);
  }
}

