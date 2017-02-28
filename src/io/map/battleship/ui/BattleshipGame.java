package io.map.battleship.ui;

import io.map.battleship.ui.utils.Globals;
import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.test.Player;
import io.map.battleship.ui.views.BattleshipView;
import io.map.battleship.ui.views.BattleshipViewFactory;

public class BattleshipGame {

    private Board primaryBoard;
    private Board secondaryBoard;
    private BattleshipView gameView;
    private BattleshipView preGameView;
    private BattleshipView postGameView;
    private BattleshipView currentView;
    
    private BattleshipWindow window;
    private BattleshipGameController controller;


    public BattleshipGame(BattleshipGameInitializer initializer, BattleshipViewFactory viewFactory, BattleshipGameController controller) {
        this.controller = controller;
        
        // setup boards
        primaryBoard = initializer.getPrimaryBoard();
        primaryBoard.setGame(this);

        secondaryBoard = initializer.getSecondaryBoard();
        secondaryBoard.setGame(this);
        
        controller.setPlayerBoard(secondaryBoard);
        controller.setOpponentBoard(primaryBoard);
        
        viewFactory.createViews(primaryBoard, secondaryBoard);
        
        gameView = viewFactory.getGameView();
        preGameView = viewFactory.getPreGameView();
        postGameView = viewFactory.getPostGameView();
    }

    public void setWindow(BattleshipWindow window) {
        this.window = window;

        if (currentView != null) {
            refreshView();
        }
    }

    public BattleshipGameController getController() {
        return controller;
    }

    public Board getPrimaryBoard() {
        return primaryBoard;
    }

    public Board getSecondaryBoard() {
        return secondaryBoard;
    }

    public BattleshipView getCurrentView() {
        return currentView;
    }

    public void setCurrentView(int view) {
        BattleshipView cview;
        switch (view) {
            case BattleshipView.GAME_VIEW:
                cview = gameView;
                break;
            case BattleshipView.PRE_GAME_VIEW:
                cview = preGameView;
                break;
            case BattleshipView.POST_GAME_VIEW:
                cview = postGameView;
                break;
            default:
                throw new IllegalArgumentException();
        }

        currentView = cview;
        if (currentView == null) {
            System.exit(1);
        }
        
        currentView.setModeOfBoards();

        if (window != null) {
            refreshView();
        }
    }

    public void refreshView() {
        currentView.setParentWindow(window);
        
        window.setBackground(Globals.PRIMARY_COLOR);
        window.setContentPane(currentView);
        window.setResizable(false);
        window.pack();
    }
}
