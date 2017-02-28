package io.map.battleship.ui.game.listeners.board;

import io.map.battleship.ui.BattleshipGameController;
import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.game.board.BoardContainer;
import io.map.battleship.ui.game.board.BoardEvent;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class GameBoardListener extends BoardListener {
    
    public GameBoardListener(Board board, BoardContainer container) {
        super(board, container);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.printf("Pressed at x:%d y:%d\n", e.getX(), e.getY());
        Point point = container.getGridCoordinateFrom(e);

        BoardEvent be = new BoardEvent();
        be.setX(point.x);
        be.setY(point.y);
        
        BattleshipGameController controller = board.getGame().getController();
        if (controller != null) {
            controller.getUpdateSender().onPrimaryBoardClick(be);
            controller.getUpdateReceiver().waitForReply();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.printf("Released at x:%d y:%d\n", e.getX(), e.getY());
        Point point = container.getGridCoordinateFrom(e);
        
        BoardEvent be = new BoardEvent();
        be.setX(point.x);
        be.setY(point.y);

        BattleshipGameController controller = board.getGame().getController();
        if (controller != null) {
            controller.getUpdateSender().onPrimaryBoardClick(be);
            controller.getUpdateReceiver().waitForReply();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
