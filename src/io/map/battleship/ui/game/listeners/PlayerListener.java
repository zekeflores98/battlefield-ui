package io.map.battleship.ui.game.listeners;

import io.map.battleship.ui.game.board.BoardEvent;
import io.map.battleship.ui.game.ships.RevealShipEvent;

public interface PlayerListener {
    
    void waitForReply();
    
    void replyReceived();
    
    void onReady();
    
    void onPaused();
    
    void onContinued();
    
    void onQuitted();
    
    void onTurnStart();
    
    void onTurnFinish();
    
    void revealShip(RevealShipEvent e);
    
    void onPrimaryBoardClick(BoardEvent e);
    
    void onHit(BoardEvent e);
    
    void onMiss(BoardEvent e);
}
