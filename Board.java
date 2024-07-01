package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    private Image mouse;
    private Image bdot;
    private Image head;

    private final int ALLdots = 1000;
    private final int dotsize = 10;
    private final int randomPos = 30;
    private int appleX;
    private int appleY;
    private final int x[] = new int[ALLdots];
    private final int y[] = new int[ALLdots];
    
    private boolean inGame = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean rightDirection = true;
    private boolean leftDirection = false;

    private int dots = 3;
    private int score = 0; // Score variable to keep track of the score
    private Timer timer;
    private SnakeGame parent; // Reference to parent JFrame

    Board(SnakeGame parent) {
        this.parent = parent;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                }

                if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                }

                if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                    upDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                }

                if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                    downDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                }
            }
        });
        setBackground(Color.BLACK);
        setFocusable(true);
        loadImages();
        initGame();
    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/DotG.png"));
        bdot = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icon/redD.png"));
        head = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icon/apple.png"));
        mouse = i3.getImage();
    }

    public void initGame() {
        dots = 3;
        score = 0;
        inGame = true;
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;

        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * dotsize;
            y[i] = 50;
        }
        locateApple();
        timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple() {
        int r = (int) (Math.random() * randomPos);
        appleX = r * dotsize;
        r = (int) (Math.random() * randomPos);
        appleY = r * dotsize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(mouse, appleX, appleY, this);

            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(bdot, x[i], y[i], this);
                }
            }

            // Draw the score
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", Font.BOLD, 14));
            g.drawString("Score: " + score, 10, 20);

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (500 - metr.stringWidth(msg)) / 2, 500 /4);

        // Draw the final score
        g.drawString("Final Score: " + score, (500 - metr.stringWidth("Final Score: " + score)) / 2, 500 /4 + 60);

        // Add buttons for replay and exit
        parent.showButtons(true);
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (leftDirection) {
            x[0] -= dotsize;
        }

        if (rightDirection) {
            x[0] += dotsize;
        }

        if (upDirection) {
            y[0] -= dotsize;
        }

        if (downDirection) {
            y[0] += dotsize;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            dots++;
            score++; // Increment score when apple is eaten
            locateApple();
        }
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }

        if (y[0] >= 500) {
            inGame = false;
        }

        if (x[0] >= 500) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }
}
