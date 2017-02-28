package io.map.battleship.ui.views;

import io.map.battleship.ui.game.board.Board;

public abstract class BattleshipViewFactory {
    private GameView gameView;
    private PreGameView preGameView;
    private PostGameView postGameView;
    
    private Board primaryBoard, secondaryBoard;
    
    public abstract GameView createGameView();
    public abstract PreGameView createPreGameView();
    public abstract PostGameView createPostGameView();

    public Board getPrimaryBoard() {
        return primaryBoard;
    }

    public Board getSecondaryBoard() {
        return secondaryBoard;
    }

    public GameView getGameView() {
        return gameView;
    }

    public PreGameView getPreGameView() {
        return preGameView;
    }

    public PostGameView getPostGameView() {
        return postGameView;
    }
    
    public void createViews(Board primaryBoard, Board secondaryBoard) {
        this.primaryBoard = primaryBoard;
        this.secondaryBoard = secondaryBoard;
        
        gameView = createGameView();
        preGameView = createPreGameView();
        postGameView = createPostGameView();
    }
}
