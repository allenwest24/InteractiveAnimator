package cs3500.animator.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DropDownPanel extends JMenu implements ActionListener {
  private ViewDelegate delegate;

  public DropDownPanel(ViewDelegate delegate) {
    super("Settings");
    this.delegate = delegate;
    // Start the animation wherever we left off.
    JMenuItem play = new JMenuItem("Play");
    // Stop wherever the animation is currently at.
    JMenuItem pause = new JMenuItem("Pause");
    // This will be a checkbox of a boolean loophuh.
    JMenuItem loop = new JMenuItem("Loop");
    // Triggers popup menu to set the desired speed.
    JMenuItem speed = new JMenuItem("Speed");
    // On button push this will start over from the beginning. Will keep the same play/pause state.
    JMenuItem reset = new JMenuItem("Reset");
    // Future addition: keystroke.

    play.addActionListener(this::actionPerformed);
    pause.addActionListener(this::actionPerformed);
    loop.addActionListener(this::actionPerformed);
    speed.addActionListener(this::actionPerformed);
    reset.addActionListener(this::actionPerformed);

    play.setActionCommand("play");
    pause.setActionCommand("pause");
    loop.setActionCommand("loop");
    speed.setActionCommand("speed");
    reset.setActionCommand("reset");

    this.add(play);
    this.add(pause);
    this.add(loop);
    this.add(speed);
    this.add(reset);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String buttonClicked = e.getActionCommand();
    // To test if the buttons are being registered.
    //System.out.println(buttonClicked);
    switch (buttonClicked) {
      case "play":
        delegate.play();
        break;
      case "pause":
        delegate.pauseAnimation();
        break;
      case "loop":
        delegate.loopAnimation();
        break;
      case "speed":
        // delegate.setSpeed();
        break;
      case "reset":
        delegate.resetAnimation();
        break;
      default:
        //TODOallen: something here
        break;
    }
  }
}

