package io.map.battleship.ui.views;

import io.map.battleship.ui.game.board.Board;

/**
 * View to show after the game is finished. The score and the board of the
 * current players are displayed here.
 * 
 * @author bmdelacruz
 */
public class PostGameView extends BattleshipView {

    @Override
    public int getType() {
        return BattleshipView.POST_GAME_VIEW;
    }
    
    @Override
    public void setModeOfBoards() {
        if (primaryBoard != null) {
            primaryBoard.setMode(Board.STANDBY_MODE);
        }
        if (secondaryBoard != null) {
            secondaryBoard.setMode(Board.STANDBY_MODE);
        }
    }
}
