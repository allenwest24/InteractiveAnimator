package cs3500.animator.view;

import cs3500.excellence.ShapeType;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;

/**
 * Class to represent a custom JPanel view component.
 */
public final class DropDownPanel extends JMenu implements ActionListener {
  private ViewDelegate delegate;
  private JFrame dialogFrame;
  private JMenuItem loopItem;
  private boolean loopChecked;

  /**
   * Public constructor for this object.
   *
   * @param delegate    the non-null VCDelegate that owns this view.
   * @param dialogFrame the JFrame upon which the messages are presented.
   * @throws IllegalArgumentException for null params.
   */
  public DropDownPanel(ViewDelegate delegate, JFrame dialogFrame) {
    super("Settings");
    if (delegate == null || dialogFrame == null) {
      throw new IllegalArgumentException("Cannot accept null parameters.");
    }
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
    JMenuItem devCred = new JMenuItem("Credits");


    play.addActionListener(this::actionPerformed);
    pause.addActionListener(this::actionPerformed);
    loop.addActionListener(this::actionPerformed);
    speed.addActionListener(this::actionPerformed);
    reset.addActionListener(this::actionPerformed);
    addShape.addActionListener(this::actionPerformed);
    delShape.addActionListener(this::actionPerformed);
    addKeyFrame.addActionListener(this::actionPerformed);
    delKeyFrame.addActionListener(this::actionPerformed);
    devCred.addActionListener(this::actionPerformed);


    play.setActionCommand("play");
    pause.setActionCommand("pause");
    loop.setActionCommand("loop");
    speed.setActionCommand("speed");
    reset.setActionCommand("reset");
    addShape.setActionCommand("addshape");
    delShape.setActionCommand("delshape");
    addKeyFrame.setActionCommand("addkf");
    delKeyFrame.setActionCommand("delkf");
    devCred.setActionCommand("credWhereItsDue");


    this.add(play);
    this.add(pause);
    this.add(loop);
    this.add(speed);
    this.add(reset);
    this.add(addShape);
    this.add(delShape);
    this.add(addKeyFrame);
    this.add(delKeyFrame);
    this.add(devCred);

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
      case "credWhereItsDue":
        this.displayErrorInfo("Dev Creds: Luke Andrews, Allen West\n\n" +
            "       -- life is soup, i am fork");
        break;
      default:
        this.displayErrorInfo("Unknown menu option!\n");
        break;
    }
  }

  private enum ModelPrompts {
    ADD_SHAPE, DELETE_SHAPE, ADD_KEYFRAME, DELETE_KEYFRAME, GET_KEYFRAME_USER;
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
    if (s == null) {
      return;
    }
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
      case GET_KEYFRAME_USER:
        this.passOnNewParams(s);
        break;
      default:
        break;
    }
  }

  private Integer conditionalInt(String s) {
    if (s == null) {
      return null;
    }
    Integer tempVal = null;
    try {
      tempVal = Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return null;
    }
    return tempVal;
  }

  private void passOnNewParams(String s) {
    String tempx = this.deriveRelevantComponent(StringComponents.POSX, s);
    String tempy = this.deriveRelevantComponent(StringComponents.POSY, s);
    String tempw = this.deriveRelevantComponent(StringComponents.WIDTH, s);
    String temph = this.deriveRelevantComponent(StringComponents.HEIGHT, s);
    String tempr = this.deriveRelevantComponent(StringComponents.RED, s);
    String tempg = this.deriveRelevantComponent(StringComponents.GREEN, s);
    String tempb = this.deriveRelevantComponent(StringComponents.BLUE, s);

    Integer tempx2 = this.conditionalInt(tempx);
    Integer tempy2 = this.conditionalInt(tempy);
    Integer tempw2 = this.conditionalInt(tempw);
    Integer temph2 = this.conditionalInt(temph);
    Integer tempr2 = this.conditionalInt(tempr);
    Integer tempg2 = this.conditionalInt(tempg);
    Integer tempb2 = this.conditionalInt(tempb);

    System.out.println(s);
    if ((s != null) && (tempx2 == null || tempy2 == null || tempw2 == null || temph2 == null
        || tempr2 == null || tempg2 == null || tempb2 == null)) {
      this.delegate.passNewValuesOnKeyFrame(false, null, null,
          null, null, null, null, null);
      this.displayErrorInfo("Error 404: Clever line not found\n");
      return;
    } else {
      boolean success = this.delegate.passNewValuesOnKeyFrame(true, tempx2, tempy2, tempw2,
          temph2, tempr2, tempg2, tempb2);
    }
  }

  private void addKeyFramePrompt(String s) {
    String shapeName = this.deriveRelevantComponent(StringComponents.SHAPE_NAME, s);
    String shapeTick = this.deriveRelevantComponent(StringComponents.TICK, s);
    Integer i = null;
    if (shapeName == null || shapeTick == null || !this.delegate.doesShapeExistForName(shapeName)) {
      this.displayErrorInfo("Must give an existing shape to add a KeyFrame to!\n");
      return;
    } else {
      try {
        i = Integer.parseInt(shapeTick);
      } catch (NumberFormatException e) {
        this.displayErrorInfo("That tick be janky yo!\n");
        return;
      }
      if (i < 0) {
        this.displayErrorInfo("Invalid KeyFrame for shape!\n");
        return;
      }
      String st = delegate.canAddKeyFrameAtTick(shapeName, i)
          == null ? "0,0,0,0,0,0,0" : delegate.canAddKeyFrameAtTick(shapeName, i);
      this.promptUserForChangeToModel(ModelPrompts.GET_KEYFRAME_USER,
          "pos=(10,10), size=(50,50), color=(255,0,0) "
              + "in format of 0,0,0,0,0,0,0 : \n",
          "Add the desired KeyFrame parameters.\n",
          st);
    }
  }

  private void deleteKeyFramePrompt(String s) {
    String shapeName = this.deriveRelevantComponent(StringComponents.SHAPE_NAME, s);
    String shapeTick = this.deriveRelevantComponent(StringComponents.TICK, s);
    Integer i = null;
    if (shapeName == null || shapeTick == null || !this.delegate.doesShapeExistForName(shapeName)) {
      this.displayErrorInfo("Must give an existing shape to delete!\n");
      return;
    } else {
      try {
        i = Integer.parseInt(shapeTick);
      } catch (NumberFormatException e) {
        this.displayErrorInfo("That tick be janky yo!\n");
        return;
      }
      if (!this.delegate.deleteKeyFrame(shapeName, i)) {
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
    } else if (s != null && (i == null || i <= 0)) {
      this.displayErrorInfo("Input must be a positive number!\n");
    }
  }

  private void displayErrorInfo(String s) {
    JOptionPane.showMessageDialog(this.dialogFrame, s);
  }
}

