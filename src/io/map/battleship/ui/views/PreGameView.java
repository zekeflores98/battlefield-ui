package io.map.battleship.ui.views;

import io.map.battleship.ui.game.board.Board;

/**
 * View to show before the game starts. The setup of the board of
 * the user is setup here.
 * 
 * @author bmdelacruz
 */
public class PreGameView extends BattleshipView {

    @Override
    public int getType() {
        return BattleshipView.PRE_GAME_VIEW;
    }
    
    @Override
    public void setModeOfBoards() {
        if (primaryBoard != null) {
            primaryBoard.setMode(Board.EDIT_MODE);
        }
        if (secondaryBoard != null) {
            secondaryBoard.setMode(Board.STANDBY_MODE);
        }
    }
}
