import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class FlyingButtons extends JFrame {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int BUTTON_SIZE = 50;
    private final int SPEED = 5;
    private final int NUM_BUTTONS = 5;
    private JButton[] buttons;

    public FlyingButtons() {
        setTitle("Flying Buttons");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        buttons = new JButton[NUM_BUTTONS];
        Random rand = new Random();

        for (int i = 0; i < NUM_BUTTONS; i++) {
            JButton button = new JButton();
            button.setSize(BUTTON_SIZE, BUTTON_SIZE);
            button.setLocation(rand.nextInt(WIDTH - BUTTON_SIZE), rand.nextInt(HEIGHT - BUTTON_SIZE));
            button.setBackground(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            button.addActionListener(new ButtonListener());
            add(button);
            buttons[i] = button;
        }

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (JButton button : buttons) {
                    handleBorderCollision(button);
                }
            }
        });

        setVisible(true);

        animateButtons();
    }

    private void animateButtons() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JButton button : buttons) {
                    int dx = SPEED;
                    int dy = SPEED;

                    if (Math.random() < 0.5) {
                        dx *= -1;
                    }
                    if (Math.random() < 0.5) {
                        dy *= -1;
                    }

                    button.setLocation(button.getX() + dx, button.getY() + dy);

                    handleBorderCollision(button);
                }
            }
        });
        timer.start();
    }

    private void handleBorderCollision(JButton button) {
        if (button.getX() <= 0 || button.getX() >= WIDTH - button.getWidth()) {
            button.setLocation(Math.max(0, Math.min(WIDTH - button.getWidth(), button.getX())), button.getY());
        }
        if (button.getY() <= 0 || button.getY() >= HEIGHT - button.getHeight()) {
            button.setLocation(button.getX(), Math.max(0, Math.min(HEIGHT - button.getHeight(), button.getY())));
        }
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            clickedButton.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new FlyingButtons();
    }
}
