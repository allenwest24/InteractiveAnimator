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
  private boolean currDataObtianed;

  public DropDownPanel(ViewDelegate delegate, JFrame dialogFrame) {
    super("Settings");
    this.dialogFrame = dialogFrame;
    this.delegate = delegate;
    this.currDataObtianed = false;
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
            "shapeName");
        break;
      case "addkf":
        this.promptUserForChangeToModel(ModelPrompts.ADD_KEYFRAME,
            "Input shape name and tick to add keyframe:\n",
            "Add a KeyFrame.\n",
            "shapeName,shapeTick");
        break;
      case "delkf":
        this.promptUserForChangeToModel(ModelPrompts.DELETE_KEYFRAME,
            "Input shape name and tick to remove keyframe:\n",
            "Delete a KeyFrame.\n",
            "shapeName,shapeTick");
        break;
      default:
        this.displayErrorInfo("Unknown menu option!\n");
        break;
    }
  }

  private enum ModelPrompts {
    ADD_SHAPE, DELETE_SHAPE, ADD_KEYFRAME, DELETE_KEYFRAME;
  }

  private enum StringComponents {
    SHAPE_NAME, SHAPE_TYPE, TICK, POSX, POSY, WIDTH, HEIGHT, RED, GREEN, BLUE;
  }

  private String deriveRelevantComponent(StringComponents comp, String userInput) {
    String[] parsedInput;
    try {
      parsedInput = userInput.split(",");
    } catch (PatternSyntaxException e) {
      return null;
    } catch (NullPointerException e) {
      return null;
    }
    if (parsedInput.length == 0 || parsedInput.length > 7 || !userInput.contains(",")) {
      return null;
    }
    switch (comp) {
      case POSX:
      case SHAPE_NAME:
        if (parsedInput.length >= 1) {
          return parsedInput[0];
        } else {
          return null;
        }
      case TICK:
      case POSY:
      case SHAPE_TYPE:
        if (parsedInput.length >= 2) {
          return parsedInput[1];
        } else {
          return null;
        }
      case WIDTH:
        if (parsedInput.length >= 3) {
          return parsedInput[2];
        } else {
          return null;
        }
      case HEIGHT:
        if (parsedInput.length >= 4) {
          return parsedInput[3];
        } else {
          return null;
        }
      case RED:
        if (parsedInput.length >= 5) {
          return parsedInput[4];
        } else {
          return null;
        }
      case GREEN:
        if (parsedInput.length >= 6) {
          return parsedInput[5];
        } else {
          return null;
        }
      case BLUE:
        if (parsedInput.length >= 7) {
          return parsedInput[6];
        } else {
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
    switch (type) {
      case ADD_SHAPE:
        if (delegate.doesShapeExistForName(s)) {
          this.displayErrorInfo("Shape by this name already exists!\n");
          return;
        } else {
          String name = this.deriveRelevantComponent(StringComponents.SHAPE_NAME, s);
          String shapeTypeString = this.deriveRelevantComponent(StringComponents.SHAPE_TYPE, s);
          if (name == null || shapeTypeString == null) {
            this.displayErrorInfo("Oops! Something went wrong with that request! Make sure the"
                + " request follows the format: \"name,type\"\n");
            return;
          }
          ShapeType shapeType = ShapeType.optionallyDeriveShapeType(shapeTypeString);
          if (shapeType == null) {
            this.displayErrorInfo("Invalid shape type!\n");
            return;
          }
          delegate.userRequestsAddShape(shapeType, name);
        }
        break;
      case DELETE_SHAPE:
        if (delegate.doesShapeExistForName(s)) {
          delegate.userRequestsDeleteShape(s);
        } else {
          this.displayErrorInfo("Must give an existing shape to delete!\n");
          return;
        }
        break;
      case DELETE_KEYFRAME:
        this.deleteKeyFramePrompt(s);
        break;
      case ADD_KEYFRAME:
        this.addKeyFramePrompt(s);
        break;
      default:
        break;
    }
  }

  private void addKeyFramePrompt(String s) {
    String shapeName = this.deriveRelevantComponent(StringComponents.SHAPE_NAME, s);
    String shapeTick = this.deriveRelevantComponent(StringComponents.TICK, s);
    Integer i = null;
    if(shapeName == null || shapeTick == null || !this.delegate.doesShapeExistForName(shapeName)) {
      this.displayErrorInfo("Must give an existing shape to add a KeyFrame to!\n");
      return;
    }
    else {
      try {
        i = Integer.parseInt(shapeTick);
      } catch (NumberFormatException e) {
        this.displayErrorInfo("That tick be janky yo!\n");
        return;
      }
      if(i < 0) {
        this.displayErrorInfo("Invalid KeyFrame for shape!\n");
        return;
      }
      String st = delegate.canAddKeyFrameAtTick(shapeName, i);
      if(st == null) {
        
      }
    }
  }

  private void deleteKeyFramePrompt(String s) {
    String shapeName = this.deriveRelevantComponent(StringComponents.SHAPE_NAME, s);
    String shapeTick = this.deriveRelevantComponent(StringComponents.TICK, s);
    Integer i = null;
    if(shapeName == null || shapeTick == null || !this.delegate.doesShapeExistForName(shapeName)) {
      this.displayErrorInfo("Must give an existing shape to delete!\n");
      return;
    }
    else {
      try {
        i = Integer.parseInt(shapeTick);
      } catch (NumberFormatException e) {
        this.displayErrorInfo("That tick be janky yo!\n");
        return;
      }
      if(!this.delegate.deleteKeyFrame(shapeName, i)) {
        this.displayErrorInfo("This is not the KeyFrame you are looking for...\n");
        return;
      }
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

