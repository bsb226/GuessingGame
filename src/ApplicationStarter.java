/**
 * File: ApplicationStarter.java
 * Author: Brian Borowski
 * Date created: May 1999
 * Date last modified: January 30, 2011
 */

/**
 * Class that allows the application to run from an applet.
 */
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;

public class ApplicationStarter extends JApplet implements ActionListener {
    private static final long serialVersionUID = 1L;
    private GUI gui = null;

    public void init() {
        final JButton startButton = new JButton(
            "Start Guessing Game");
        startButton.setMnemonic('S');
        final Container contentPane = getContentPane();
        contentPane.setBackground(
            getBackgroundColor(getParameter("background")));
        contentPane.setLayout(new FlowLayout());
        contentPane.add(startButton);
        startButton.addActionListener(this);
    }

    public void close() {
        gui.setVisible(false);
        gui.dispose();
        gui = null;
    }

    public void actionPerformed(final ActionEvent ae) {
        if (gui == null) {
            gui = new GUI(this);
        } else {
            gui.toFront();
        }
    }

    private Color getBackgroundColor(final String background) {
        if ((background == null) || (background.length() != 7) ||
            (background.charAt(0) != '#')) {
            return Color.white;
        } else {
            String hexcolor, red, green, blue;
            hexcolor = background.substring(1, background.length());
            red = hexcolor.substring(0, 2);
            green = hexcolor.substring(2, 4);
            blue = hexcolor.substring(4, 6);
            return new Color(Integer.parseInt(red, 16),
                             Integer.parseInt(green, 16),
                             Integer.parseInt(blue, 16));
        }
    }
}
