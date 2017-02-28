package io.map.battleship.ui;

import javax.swing.JFrame;

public class BattleshipWindow extends JFrame {
    
    public static int DEFAULT_WIDTH = 800;
    public static int DEFAULT_HEIGHT = 400;
    
    private BattleshipGame game;
    
    public BattleshipWindow() {
        initializeComponents();
    }

    public void setGame(BattleshipGame game) {
        this.game = game;
        this.game.setWindow(this);
    }
    
    public void start() {
        setVisible(true);
    }
    
    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
    }
}
