
package snakegame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SnakeGame extends JFrame{
    private Board board;
    private JPanel buttonPanel;
    private JButton replayButton;
    private JButton exitButton;

    SnakeGame(){
        super("Snake Game: Let's play");// set title of game 
        board = new Board(this);
        add(board);
        setResizable(false);
        pack();
        //inbuilt function// to refresh the frame when changes done
        //setvisible do same work but we can't use it many times to update frame
        setSize(500,500);// width,height
        setLocationRelativeTo(null);//c// so that frame align with centre relative to screen
        //setVisible(true);//making frame visible=true kyuki frame hota hai bs dikhta ni hai
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        replayButton = new JButton("Replay");
        exitButton = new JButton("Exit");

        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.initGame();
                showButtons(false);
                board.requestFocusInWindow();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(replayButton);
        buttonPanel.add(exitButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        showButtons(false);
    }

    public void showButtons(boolean show) {
        replayButton.setVisible(show);
        exitButton.setVisible(show);
        buttonPanel.setVisible(show);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new SnakeGame();
            ex.setVisible(true);
        });
    }
}