/**
 * File: GUI.java
 * Author: Brian Borowski
 * Date created: May 1999
 * Date last modified: January 30, 2011
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame {
    public static final String appName = "The Guessing Game",
                               NO_DATA = "__________";

    private static final long serialVersionUID = 1L;

    private final ApplicationStarter applicationStarter;
    private final JLabel attemptsLabel, resultLabel;
    private final JTextField guessField;
    private final int low = 1, high = 100;
    private GuessingGame guessingGame;

    public GUI(final ApplicationStarter appStarter) {
        super(appName);
        this.applicationStarter = appStarter;
        guessingGame = new GuessingGame(low, high);

        final JLabel titleLabel = new JLabel(appName, JLabel.CENTER);
        titleLabel.setFont(new Font("sansserif", Font.BOLD, 26));

        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(titleLabel);

        guessField = new JTextField(3);
        guessField.addKeyListener(new KeyListener() {
            public void keyPressed(final KeyEvent ke) {
                if (KeyEvent.getKeyText(ke.getKeyCode()).equals("Enter")) {
                    processInput();
                }
            }
            public void keyReleased(final KeyEvent ke) { }
            public void keyTyped(final KeyEvent ke) { }
        });

        final JLabel promptLabel = new JLabel(
            "Guess the number the computer picked (" + low + ".." +
            high + "):");
        promptLabel.setDisplayedMnemonic('G');
        promptLabel.setLabelFor(guessField);

        final JButton okButton = new JButton("OK");
        okButton.setMnemonic('O');
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent ae) {
                processInput();
            }
        });

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(promptLabel);
        inputPanel.add(guessField);
        inputPanel.add(okButton);

        final JLabel attemptsPromptLabel =
            new JLabel("Number of attempts: ", JLabel.RIGHT);
        attemptsLabel = new JLabel(NO_DATA, JLabel.LEFT);

        final JLabel resultPromptLabel =
            new JLabel("Your guess was: ", JLabel.RIGHT);
        resultLabel = new JLabel(NO_DATA, JLabel.LEFT);

        final JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(1, 2));
        resultPanel.add(resultPromptLabel);
        resultPanel.add(resultLabel);

        final JPanel attemptsPanel = new JPanel();
        attemptsPanel.setLayout(new GridLayout(1, 2));
        attemptsPanel.add(attemptsPromptLabel);
        attemptsPanel.add(attemptsLabel);

        final JButton resetButton = new JButton("Reset");
        resetButton.setMnemonic('R');
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent ae) {
                resetGame();
                guessField.requestFocus();
            }
        });

        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1));
        centerPanel.add(inputPanel);
        centerPanel.add(resultPanel);
        centerPanel.add(attemptsPanel);
        centerPanel.setBorder(BorderFactory.createEtchedBorder());

        final JPanel resetButtonPanel = new JPanel();
        resetButtonPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 0, 0);
        resetButtonPanel.add(resetButton, gbc);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add("North", titlePanel);
        mainPanel.add("Center", centerPanel);
        mainPanel.add("South", resetButtonPanel);

        final Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(mainPanel);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new ClosingWindowListener(this));
        validate();
        pack();
        setResizable(false);
        final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        final Point p = new Point((int)((d.getWidth() - this.getWidth())/2),
                            (int)((d.getHeight() - this.getHeight())/2));
        setLocation(p);
        setVisible(true);
    }

    private void doApplicationClosing(final JFrame parent) {
        if (applicationStarter != null) {
            applicationStarter.close();
        } else {
            System.exit(0);
        }
    }

    private void resetGame() {
        guessingGame = new GuessingGame(low, high);
        guessField.setText("");
        resultLabel.setText(NO_DATA);
        attemptsLabel.setText(NO_DATA);
    }

    private void processInput() {
        final String guessStr = guessField.getText().trim();
        if (guessStr.equals("")) {
            new MessageBox(this, "Error",
                "You have not entered a number!", MessageBox.EXCLAIM);
            guessField.selectAll();
            return;
        }

        int guess = 0, result = 0;
        try {
            guessField.setText(guessStr);
            guessField.requestFocus();
            guess = Integer.parseInt(guessStr);
            result = guessingGame.guessNumber(guess);
        } catch (final NumberFormatException nfe) {
            new MessageBox(this, "Error",
                "Invalid input for guess.", MessageBox.EXCLAIM);
            guessField.selectAll();
            return;
        } catch (final GuessingGameException gge) {
            new MessageBox(this, "Error", gge.getMessage(), MessageBox.EXCLAIM);
            guessField.selectAll();
            return;
        }

        final int attempts = guessingGame.getNumberOfAttempts();
        attemptsLabel.setText(Integer.toString(attempts));
        if (result == GuessingGame.EQUAL) {
            resultLabel.setText("correct!");
            if (attempts == 1) {
                new MessageBox(this, "Congratulations",
                    "You guessed the number in " + attempts + " try.",
                    MessageBox.SMILE);
            } else if (attempts <= guessingGame.getMaxAttemptsForSuccess()) {
                new MessageBox(this, "Congratulations",
                    "You guessed the number in " + attempts + " attempts.",
                    MessageBox.SMILE);
            } else {
                new MessageBox(this, "Boo!",
                    "You guessed the number, but it took you " +
                    attempts + " attempts.", MessageBox.FROWN);
            }
            resetGame();
        } else if (result == GuessingGame.TOO_LOW) {
            resultLabel.setText("too low");
        } else {
            resultLabel.setText("too high");
        }
        guessField.selectAll();
    }

    class ClosingWindowListener implements WindowListener {
        private final JFrame parent;

        public ClosingWindowListener(final JFrame parent) {
            this.parent = parent;
        }

        public void windowClosing(final WindowEvent e) {
            doApplicationClosing(parent);
        }

        public void windowDeactivated(final WindowEvent e) { }

        public void windowActivated(final WindowEvent e) { }

        public void windowDeiconified(final WindowEvent e) { }

        public void windowIconified(final WindowEvent e) { }

        public void windowClosed(final WindowEvent e) { }

        public void windowOpened(final WindowEvent e) { }
    }
}
