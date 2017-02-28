package io.map.battleship.ui;

import io.map.battleship.ui.game.listeners.BattleshipGameUpdateReceiver;
import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.game.listeners.PlayerListener;

public class BattleshipGameController {

    private Board playerBoard;
    private Board opponentBoard;
    private PlayerListener updateSender;
    private PlayerListener updateReceiver;
    
    public BattleshipGameController() {
        updateReceiver = new BattleshipGameUpdateReceiver(this);
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }
    
    public void setPlayerBoard(Board playerBoard) {
        this.playerBoard = playerBoard;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }
    
    public void setOpponentBoard(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
    }

    public PlayerListener getUpdateSender() {
        return updateSender;
    }

    public void setUpdateSender(PlayerListener updateSender) {
        this.updateSender = updateSender;
    }

    public PlayerListener getUpdateReceiver() {
        return updateReceiver;
    }
}
