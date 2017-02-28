package io.map.battleship.ui.game.listeners;

import io.map.battleship.ui.BattleshipGameController;
import io.map.battleship.ui.game.board.BoardEvent;
import io.map.battleship.ui.game.listeners.PlayerListener;
import io.map.battleship.ui.game.markers.HitMarker;
import io.map.battleship.ui.game.markers.Marker;
import io.map.battleship.ui.game.markers.MissMarker;
import io.map.battleship.ui.game.ships.RevealShipEvent;
import io.map.battleship.ui.game.ships.Ship;

public class BattleshipGameUpdateReceiver implements PlayerListener {
    
    private BattleshipGameController controller;
    
    public BattleshipGameUpdateReceiver(BattleshipGameController controller) {
        this.controller = controller;
    }

    @Override
    public void onReady() {
    }

    @Override
    public void onPaused() {
    }

    @Override
    public void onContinued() {
    }

    @Override
    public void onQuitted() {
    }

    @Override
    public void onTurnStart() {
    }

    @Override
    public void onTurnFinish() {
    }

    @Override
    public void revealShip(RevealShipEvent e) {
        Ship ship = controller.getOpponentBoard().getShip(e.getShipType());
        ship.setOrientation(e.getOrientation());
        ship.setX(e.getX());
        ship.setY(e.getY());
        ship.setVisible(true);
        
        controller.getOpponentBoard().getContainer().repaint();
        replyReceived();
    }

    @Override
    public void onPrimaryBoardClick(BoardEvent e) {
        Ship ship = controller.getPlayerBoard().getShipAt(e.getX(), e.getY());
        if (ship != null) {
            controller.getUpdateSender().onHit(e);
            
            Marker marker = new HitMarker(controller.getPlayerBoard());
            marker.setLocation(e.getX(), e.getY());
            controller.getPlayerBoard().addMarker(marker);

            // check if ship was revealed then call `revealShip` if true.
            if (ship.canBeRevealedIn(controller.getPlayerBoard())) {
                RevealShipEvent rse = new RevealShipEvent();
                rse.setOrientation(ship.getOrientation());
                rse.setShipType(ship.getType());
                rse.setX(ship.getX());
                rse.setY(ship.getY());

                controller.getUpdateSender().revealShip(rse);
            }
        } else {
            controller.getUpdateSender().onMiss(e);
            
            Marker marker = new MissMarker(controller.getPlayerBoard());
            marker.setLocation(e.getX(), e.getY());
            controller.getPlayerBoard().addMarker(marker);
        }
        
        controller.getPlayerBoard().getContainer().repaint();
    }

    @Override
    public void onHit(BoardEvent e) {
        Marker marker = new HitMarker(controller.getOpponentBoard());
        marker.setLocation(e.getX(), e.getY());
        controller.getOpponentBoard().addMarker(marker);

        controller.getOpponentBoard().getContainer().repaint();
        replyReceived();
    }

    @Override
    public void onMiss(BoardEvent e) {
        Marker marker = new MissMarker(controller.getOpponentBoard());
        marker.setLocation(e.getX(), e.getY());
        controller.getOpponentBoard().addMarker(marker);

        controller.getOpponentBoard().getContainer().repaint();
        replyReceived();
    }

    @Override
    public void waitForReply() {
//        opponentBoard.setMode(Board.STANDBY_MODE);
    }

    @Override
    public void replyReceived() {
//        opponentBoard.setMode(Board.GAME_MODE);
    }
}
