package io.map.battleship.ui;

import io.map.battleship.ui.game.Board;
import io.map.battleship.ui.views.GameView;
import javax.swing.JFrame;

public class BattleshipWindow extends JFrame {
    
    public static int DEFAULT_WIDTH = 800;
    public static int DEFAULT_HEIGHT = 400;
    
    private BattleshipView currentView, gameView, preGameView, postGameView;
    
    public BattleshipWindow() {
        initializeComponents();
    }
    
    public void start() {
        setVisible(true);
    }
    
    public void setCurrentView(int view) {
        BattleshipView currentView;
        if (view == BattleshipView.GAME_VIEW) {
            currentView = gameView;
        } else if (view == BattleshipView.PRE_GAME_VIEW) {
            currentView = preGameView;
        } else if (view == BattleshipView.POST_GAME_VIEW) {
            currentView = postGameView;
        } else {
            throw new IllegalArgumentException();
        }
        
        this.currentView = currentView;
        this.currentView.setParentWindow(this);
        
        setContentPane(this.currentView);
        pack();
    }
    
    private void initializeComponents() {
        // behaviour
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        
        // dimensions
        setResizable(false);
        
        // look
        setBackground(Globals.PRIMARY_COLOR);
        
        // setup views
        gameView = new GameView(this);
        setCurrentView(BattleshipView.GAME_VIEW);
        
        Board primaryBoard = ((GameView)gameView).getPrimaryBoard();
        primaryBoard.setMode(Board.EDIT_MODE);
        
        Board secondaryBoard = ((GameView)gameView).getSecondaryBoard();
        secondaryBoard.setMode(Board.OWNER_MODE);
    }
}
