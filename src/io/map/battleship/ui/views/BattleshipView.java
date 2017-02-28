package io.map.battleship.ui.views;

import io.map.battleship.ui.BattleshipWindow;
import io.map.battleship.ui.game.board.Board;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class BattleshipView extends JPanel {
    
    public static final int GAME_VIEW = 1;
    public static final int PRE_GAME_VIEW = 2;
    public static final int POST_GAME_VIEW = 3;

    private BattleshipWindow window;
    
    protected Board primaryBoard;
    protected Board secondaryBoard;
    
    public BattleshipView() {
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    }
    
    public BattleshipView(Board primaryBoard, Board secondaryBoard) {
        this.primaryBoard = primaryBoard;
        this.secondaryBoard = secondaryBoard;
        
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    }
    
    public abstract int getType();
    
    public abstract void setModeOfBoards();

    public Board getPrimaryBoard() {
        return primaryBoard;
    }

    public Board getSecondaryBoard() {
        return secondaryBoard;
    }
    
    public void setParentWindow(BattleshipWindow window) {
        this.window = window;
    }
}
