package io.map.battleship.ui;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.views.BattleshipViewFactory;

public abstract class BattleshipGameInitializer {
    
    public abstract BattleshipViewFactory getViewFactory();
    
    public abstract BattleshipGameController getController();
    
    public abstract BattleshipGame createGame(BattleshipViewFactory viewFactory, BattleshipGameController controller);
    
    public abstract void onGameCreated(BattleshipGame game);
    
    public void initialize() {
        BattleshipViewFactory viewFactory = getViewFactory();
        BattleshipGameController controller = getController();
        BattleshipGame game = createGame(viewFactory, controller);
        
        onGameCreated(game);
    }
    
    public Board getPrimaryBoard() {
        Board board = new Board();
        board.setMode(Board.STANDBY_MODE);
        
        return board;
    }
    
    public Board getSecondaryBoard() {
        Board board = new Board();
        board.setMode(Board.STANDBY_MODE);
        
        return board;
    }
}
