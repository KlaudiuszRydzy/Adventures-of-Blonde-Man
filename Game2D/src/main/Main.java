package main;

import javax.swing.*;

public class Main {
    public static void main(String [] args) {
        //this creates the window that the game is played on and
        // sets up the `thread to start the updates for the game
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The Adventures of Blonde Man");
        GameScreen gameScreen = new GameScreen();
        window.add(gameScreen);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gameScreen.setupGame();
        gameScreen.startGameThread();
    }

}
