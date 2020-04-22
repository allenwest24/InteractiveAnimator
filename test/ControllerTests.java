import cs3500.animator.controller.EditorViewController;
import cs3500.animator.controller.IViewController;
import cs3500.animator.controller.VCDelegate;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.EditorView;
import cs3500.animator.view.IView;
import cs3500.excellence.ShapeType;
import cs3500.excellence.IAnimation;
import cs3500.excellence.Shape;
import cs3500.excellence.Motion;
import cs3500.excellence.AnimationDelegate;
import cs3500.excellence.AnimationModel;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Class to test the IviewController and VCDelegate interface methods.
 */
public class ControllerTests {
  AnimationBuilder<IAnimation<ShapeType>> builder;
  IAnimation<ShapeType> model;
  AnimationDelegate<Shape, Motion> delegateRef;
  IView view;
  VCDelegate<ShapeType> controller;
  IViewController controllerStartable;

  void setUp(String file, int speed) {
    FileReader readableFile = null;
    try {
      readableFile = new FileReader("src/" + file);
    } catch (FileNotFoundException e) {
      System.out.println("Failed to find file.");
    }
    builder = new AnimationModel.Builder();
    model = AnimationReader.parseFile(readableFile, builder);
    delegateRef = (AnimationDelegate<Shape, Motion>) model;
    view = new EditorView(delegateRef, speed);
    controllerStartable = new EditorViewController(model, delegateRef, view);
    controller = (VCDelegate<ShapeType>) controllerStartable;
  }

  @Test
  public void testAddingShape() {
    setUp("smalldemo.txt", 10);
    int numLines = this.delegateRef.retrieveCurrentGameState().keySet().size();
    this.controller.userRequestsAddShape(ShapeType.RECTANGLE, "L", 0);
    int numLineAfterAddition = this.delegateRef.retrieveCurrentGameState().keySet().size();
    assertTrue(numLines + 1 == numLineAfterAddition);
  }

  @Test
  public void testAddingExistingShapeDoesNothing() {
    setUp("smalldemo.txt", 10);
    int numLines = this.delegateRef.retrieveCurrentGameState().keySet().size();
    this.controller.userRequestsAddShape(ShapeType.RECTANGLE, "R", 0);
    int numLineAfterAddition = this.delegateRef.retrieveCurrentGameState().keySet().size();
    assertTrue(numLines == numLineAfterAddition);
  }

  @Test
  public void testDeletingShape() {
    setUp("smalldemo.txt", 10);
    int numLines = this.delegateRef.retrieveCurrentGameState().keySet().size();
    this.controller.userRequestsDeleteShape("R");
    int numLineAfterDeleting = this.delegateRef.retrieveCurrentGameState().keySet().size();
    assertTrue(numLines - 1 == numLineAfterDeleting);
  }

  @Test
  public void testDeletingShapeThatDoesntExist() {
    setUp("smalldemo.txt", 10);
    assertFalse(this.controller.userRequestsDeleteShape("PP"));
  }

  @Test
  public void testAddingKeyFrame() {
    setUp("smalldemo.txt", 10);
    int numLines = this.delegateRef.retrieveMotionsForObjectWithName("R").size();
    this.controller.userRequestAddKeyFrameInfo("R", 11);
    this.controller.passNewValuesOnKeyFrameAgain(true, 0, 0, 0,
        0, 0, 0, 0);
    int numLineAfterAddition = this.delegateRef.retrieveMotionsForObjectWithName("R").size();
    assertTrue(numLines + 1 == numLineAfterAddition);
  }

  @Test
  public void testAddingKeyFrameToShapeThatDoesntExist() {
    setUp("smalldemo.txt", 10);
    this.controller.userRequestAddKeyFrameInfo("R", 11);
    assertFalse(this.controller.passNewValuesOnKeyFrameAgain(false, 0, 0,
        0, 0, 0, 0, 0));
  }

  @Test
  public void testDeletingKeyFrame() {
    setUp("smalldemo.txt", 10);
    int numLines = this.delegateRef.retrieveMotionsForObjectWithName("R").size();
    this.controller.userRequestsDeleteKeyFrame("R", 10);
    int numLineAfterAddition = this.delegateRef.retrieveMotionsForObjectWithName("R").size();
    assertTrue(numLines - 1 == numLineAfterAddition);
  }

  @Test
  public void testDeletingKeyFrameFromShapeThatDoesntExist() {
    setUp("smalldemo.txt", 10);
    assertFalse(this.controller.userRequestsDeleteKeyFrame("k", 0));
  }

  @Test
  public void testAddNewLayer() {
    setUp("buildingsLayered.txt", 10);
    int prevLayerNum = delegateRef.retrieveOrderedLayers().size();
    controller.userRequestAddLayer();
    int newLayerNum = delegateRef.retrieveOrderedLayers().size();
    assertEquals(newLayerNum - 1, prevLayerNum);
  }

  @Test
  public void testDeleteLayer() {
    setUp("buildingsLayered.txt", 10);
    int prevLayerNum = delegateRef.retrieveOrderedLayers().size();
    controller.userRequestDeleteLayer(0);
    int newLayerNum = delegateRef.retrieveOrderedLayers().size();
    assertEquals(newLayerNum + 1, prevLayerNum);
  }

  @Test
  public void testDeleteBogusLayerStaysSameLength() {
    setUp("buildingsLayered.txt", 10);
    int prevLayerNum = delegateRef.retrieveOrderedLayers().size();
    controller.userRequestDeleteLayer(75);
    int newLayerNum = delegateRef.retrieveOrderedLayers().size();
    assertEquals(newLayerNum, prevLayerNum);
  }

  @Test
  public void testDeleteReturnsSuccess() {
      setUp("buildingsLayered.txt", 10);
      assertTrue(controller.userRequestDeleteLayer(0));
  }

  @Test
  public void testDeleteReturnsFailure() {
    setUp("buildingsLayered.txt", 10);
    assertFalse(controller.userRequestDeleteLayer(75));
  }

  @Test
  public void testSwapLayersWorks() {
    setUp("buildingsLayered.txt", 10);
    ArrayList<String> prevLayer = delegateRef.retrieveOrderedLayers().get(0);
    controller.userRequestSwapLayers(0,1);
    ArrayList<String> newLayer = delegateRef.retrieveOrderedLayers().get(1);
    assertEquals(prevLayer, newLayer);
  }

  @Test
  public void testSwapLayersBad() {
    setUp("buildingsLayered.txt", 10);
    ArrayList<String> prevLayer = delegateRef.retrieveOrderedLayers().get(0);
    controller.userRequestSwapLayers(0,75);
    ArrayList<String> newLayer = delegateRef.retrieveOrderedLayers().get(1);
    assertNotEquals(prevLayer, newLayer);
  }
}

