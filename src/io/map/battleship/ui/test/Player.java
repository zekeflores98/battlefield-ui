package io.map.battleship.ui.test;

import io.map.battleship.ui.BattleshipGame;
import io.map.battleship.ui.BattleshipGameController;
import io.map.battleship.ui.BattleshipGameInitializer;
import io.map.battleship.ui.BattleshipWindow;
import io.map.battleship.ui.views.BattleshipView;
import io.map.battleship.ui.views.BattleshipViewFactory;
import io.map.battleship.ui.views.GameView;
import io.map.battleship.ui.views.PostGameView;
import io.map.battleship.ui.views.PreGameView;

public class Player extends BattleshipGameInitializer {

    private BattleshipGame game;

    public BattleshipGame getGame() {
        return game;
    }
    
    @Override
    public BattleshipViewFactory getViewFactory() {
        return new BattleshipViewFactory() {
            @Override
            public GameView createGameView() {
                return new GameView(getPrimaryBoard(), getSecondaryBoard());
            }

            @Override
            public PreGameView createPreGameView() {
                return null;
            }

            @Override
            public PostGameView createPostGameView() {
                return null;
            }
        };
    }

    @Override
    public BattleshipGameController getController() {
        return new BattleshipGameController();
    }

    @Override
    public BattleshipGame createGame(BattleshipViewFactory viewFactory, BattleshipGameController controller) {
        return new BattleshipGame(this, viewFactory, controller);
    }

    @Override
    public void onGameCreated(BattleshipGame game) {
        this.game = game;
        
        BattleshipWindow window = new BattleshipWindow();
        
        window.setGame(game);
        window.start();
        
        game.setCurrentView(BattleshipView.GAME_VIEW);
    }
}
