package cs3500.animator.view;

import cs3500.excellence.Shape;
import cs3500.excellence.ShapeType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class DropDownPanel extends JMenu implements ActionListener {
  private ViewDelegate delegate;
  private JFrame dialogFrame;
  private JMenuItem loopItem;
  private boolean loopChecked;

  public DropDownPanel(ViewDelegate delegate, JFrame dialogFrame) {
    super("Settings");
    this.dialogFrame = dialogFrame;
    this.delegate = delegate;
    JMenuItem play = new JMenuItem("Play");
    JMenuItem pause = new JMenuItem("Pause");
    JMenuItem loop = new JCheckBoxMenuItem("Loop");
    this.loopItem = loop;
    this.loopChecked = false;
    loop.setSelected(this.loopChecked);
    JMenuItem speed = new JMenuItem("Speed");
    JMenuItem reset = new JMenuItem("Reset");
    JMenuItem addShape = new JMenuItem("Add Shape");
    JMenuItem delShape = new JMenuItem("Delete Shape");
    JMenuItem addKeyFrame = new JMenuItem("Add Key Frame");
    JMenuItem delKeyFrame = new JMenuItem("Delete Key Frame");


    play.addActionListener(this::actionPerformed);
    pause.addActionListener(this::actionPerformed);
    loop.addActionListener(this::actionPerformed);
    speed.addActionListener(this::actionPerformed);
    reset.addActionListener(this::actionPerformed);
    addShape.addActionListener(this::actionPerformed);
    delShape.addActionListener(this::actionPerformed);
    addKeyFrame.addActionListener(this::actionPerformed);
    delKeyFrame.addActionListener(this::actionPerformed);


    play.setActionCommand("play");
    pause.setActionCommand("pause");
    loop.setActionCommand("loop");
    speed.setActionCommand("speed");
    reset.setActionCommand("reset");
    addShape.setActionCommand("addshape");
    delShape.setActionCommand("delshape");
    addKeyFrame.setActionCommand("addkf");
    delKeyFrame.setActionCommand("delkf");


    this.add(play);
    this.add(pause);
    this.add(loop);
    this.add(speed);
    this.add(reset);
    this.add(addShape);
    this.add(delShape);
    this.add(addKeyFrame);
    this.add(delKeyFrame);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String buttonClicked = e.getActionCommand();
    switch (buttonClicked) {
      case "play":
        delegate.play();
        break;
      case "pause":
        delegate.pauseAnimation();
        break;
      case "loop":
        this.loopChecked = !this.loopChecked;
        this.loopItem.setSelected(this.loopChecked);
        delegate.loopAnimation();
        break;
      case "speed":
        this.promptUserForSpeedInput();
        break;
      case "reset":
        delegate.resetAnimation();
        break;
      case "addshape":
        this.promptUserForChangeToModel(ModelPrompts.ADD_SHAPE,
            "Input shape name and type (rectangle || ellipse) separated by a comma:\n",
            "Add a Shape.\n",
            "shapeName,shapeType");
        break;
      case "delshape":
        this.promptUserForChangeToModel(ModelPrompts.DELETE_SHAPE,
            "Input shape name to delete:\n",
            "Delete a Shape.\n",
            "Shape name");
        break;
      case "addkf":
       // this.promptUserForKeyFrameToAdd();
        break;
      case "delkf":
        //this.promptUserForKeyFrameToRemove();
        break;
      default:
        //TODOallen: something here
        break;
    }
  }

  private enum ModelPrompts {
    ADD_SHAPE, DELETE_SHAPE;
  }

  private enum StringComponents {
    SHAPE_NAME, SHAPE_TYPE;
  }

  private String deriveRelevantComponent(StringComponents comp, String userInput) {
    String[] parsedInput;
    try {
     parsedInput = userInput.split(",");
    }
    catch(PatternSyntaxException e) {
      return null;
    }
    catch(NullPointerException e) {
      return null;
    }
    if(parsedInput.length == 0 || parsedInput.length > 9 || !userInput.contains(",")) {
      return null;
    }
    switch(comp) {
      case SHAPE_NAME:
        if(parsedInput.length >= 1) {
          return parsedInput[0];
        }
        else {
          return null;
        }      case SHAPE_TYPE:
        if(parsedInput.length >= 2) {
          return parsedInput[1];
        }
        else {
          return null;
        }
      default:
        return null;
    }
  }

  private void promptUserForChangeToModel(ModelPrompts type, String prompt, String titleString,
                                          String initialVal) {
    String s = (String) JOptionPane.showInputDialog(this.dialogFrame,
        prompt,
        titleString,
        JOptionPane.PLAIN_MESSAGE,
        null, null, initialVal);
    switch(type) {
      case ADD_SHAPE:
        if(delegate.doesShapeExistForName(s)) {
          this.displayErrorInfo("Shape by this name already exists!\n");
        }
        else {
          String name = this.deriveRelevantComponent(StringComponents.SHAPE_NAME, s);
          String shapeTypeString = this.deriveRelevantComponent(StringComponents.SHAPE_TYPE, s);
          if(name == null || shapeTypeString == null) {
            this.displayErrorInfo("Oops! Something went wrong with that request! Make sure the"
                + " request follows the format: \"name,type\"\n");
            return;
          }
          ShapeType shapeType = ShapeType.optionallyDeriveShapeType(shapeTypeString);
          if(shapeType == null) {
            this.displayErrorInfo("Invalid shape type!\n");
            return;
          }
          delegate.userRequestsAddShape(shapeType, name);
        }
        break;
      case DELETE_SHAPE:
        if(delegate.doesShapeExistForName(s)) {
          delegate.userRequestsDeleteShape(s);
        }
         else {
          this.displayErrorInfo("Must give an existing shape to delete!\n");
        }
        break;
      default:
        break;
    }
  }

  private void promptUserForSpeedInput() {
    String s = (String) JOptionPane.showInputDialog(this.dialogFrame,
        "Input Number Indicating Speed:",
        "Alter speed",
        JOptionPane.PLAIN_MESSAGE,
        null, null, "1");
    Integer i = null;
    try {
      i = Integer.parseInt(s);
    } catch (NumberFormatException e) {
      if (s != null && i == null) {
        this.displayErrorInfo("Input must be a number!\n");
      }
      return;
    }
    if (i != null && i > 0) {
      delegate.setSpeed(i);
    } else if (s != null && i == null) {
      this.displayErrorInfo("Input must be a positive number!\n");
    }
  }

  private void displayErrorInfo(String s) {
    JOptionPane.showMessageDialog(this.dialogFrame, s);
  }
}

