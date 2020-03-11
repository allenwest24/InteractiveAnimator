import org.junit.Test;

import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.AnimationModel;
import cs3500.excellence.IAnimation;
import cs3500.excellence.ShapeType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



// Shit to do
// Test every possible exception lmao fuck that bullshit
// Mark all methods that yoss exceptions in javadoc
// Lot of emphasis on implicit dependencies, so we gotta make sure javadocs reflect all those
// Get rid of all print statements
// Enforce positive numbers where they should be enforced
// Make sure copy shit works out


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
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    System.out.println(viewPerspective.getStringAnimation());
    assertTrue(viewPerspective.getStringAnimation().equals("shape Oval ellipse"
            + "\n" + "shape Rect rectangle"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMotionForUndeclaredShape() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1,2,"HI",13,14,
            15,15,45,23,45,23,1,2,
            3,1,2,3);
  }

  @Test
  public void testValidMotionWithShape() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1,2,"Rect",13,14,
            15,15,45,23,45,23,1,2,
            3,1,2,3);
    System.out.println(viewPerspective.getStringAnimation());
    assertTrue(viewPerspective.getStringAnimation().equals("shape Oval ellipse"
            + "\n" + "shape Rect rectangle" + "\n"
            + "motion Rect 1 13 14 45 23 1 2 3 2 15 15 45 23 1 2 3"));
  }

  @Test
  public void testValidMotionThenShape() {
    // Copy paste above test, then add a shape after the motion
  }

  @Test
  public void testValidMotionThenShapes() {
    // Copy paste above test, then add a shape after the motion, and motions on both shapes
  }

  @Test
  public void testJustWidth() {
    animation = new AnimationModel();
    IAnimation controllerPerspective = animation;
    AnimationDelegate viewPerspective = animation;
    controllerPerspective.declareShape(ShapeType.ELLIPSE, "Oval");
    controllerPerspective.declareShape(ShapeType.RECTANGLE, "Rect");
    controllerPerspective.applyMotion(1,2,"Rect",13,14,
            15,15,45,23,45,23,1,2,
            3,1,2,3);
    controllerPerspective.applyMotion(2,3,"Rect",null,null,
            null,null,45,null,20,null,null,
            null,
            null,null,null,null);
    System.out.println(viewPerspective.getStringAnimation());
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
    controllerPerspective.applyMotion(1,2,"Rect",13,14,
            15,15,45,23,45,23,1,2,
            3,1,2,3);
    controllerPerspective.applyMotion(2,3,"Rect",null,null,
            null,null,null,null,null,null,null,
            null,
            null,null,null,null);
    System.out.println(viewPerspective.getStringAnimation());
    assertTrue(viewPerspective.getStringAnimation().equals("shape Oval ellipse"
            + "\n" + "shape Rect rectangle" + "\n"
            + "motion Rect 1 13 14 45 23 1 2 3 2 15 15 45 23 1 2 3" +
            "\n" + "motion Rect 2 15 15 45 23 1 2 3 3 15 15 45 23 1 2 3"));
  }

  // TODOluke -> Tests that we need to do
//  @Test
//  public void testMotionJustChangingColor() {
//    //TODO
//  }
//
//  @Test
//  public void testMotionJustChangingWidth() {
//    //TODO
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testTickGapException() {
//    //TODO
//  }

  // More tests
  // Overlapping interval failure (when ticks go back in time or whatever)
  // Test all added shapes are successfully added
  // Test width + color, width + height, width color + height






}

























