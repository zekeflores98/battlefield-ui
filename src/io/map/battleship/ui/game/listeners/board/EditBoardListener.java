package io.map.battleship.ui.game.listeners.board;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.game.board.BoardContainer;
import io.map.battleship.ui.game.ships.Ship;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Date;

public class EditBoardListener extends BoardListener {

    private Ship pressedShip;

    public EditBoardListener(Board board, BoardContainer container) {
        super(board, container);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.printf("Clicked at x:%d y:%d\n", e.getX(), e.getY());
        Point point = container.getGridCoordinateFrom(e);

        Ship ship = board.getShipAt(point.x, point.y);
        if (ship != null) {
            int oldOrientation = ship.getOrientation();

            switch (ship.getOrientation()) {
                case Ship.ORIENTATION_HORIZONTAL: {
                    ship.setOrientation(Ship.ORIENTATION_VERTICAL);
                    break;
                }
                case Ship.ORIENTATION_VERTICAL: {
                    ship.setOrientation(Ship.ORIENTATION_HORIZONTAL);
                    break;
                }
                default:
                    throw new AssertionError();
            }

            if (board.checkForOverlappingShips()) {
                ship.setOrientation(oldOrientation);
            }
        }

        container.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.printf("Pressed at x:%d y:%d\n", e.getX(), e.getY());
        Point point = container.getGridCoordinateFrom(e);

        Ship ship = board.getShipAt(point.x, point.y);
        if (ship != null) {
            pressedShip = ship;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.printf("Released at x:%d y:%d\n", e.getX(), e.getY());

        if (pressedShip != null && pressedShip.isBeingDragged()) {
            int oldX = pressedShip.getX();
            int oldY = pressedShip.getY();

            pressedShip.setIsBeingDragged(false);

            Point point = container.getGridCoordinateFrom(e);
            int offsetX = pressedShip.getDragOffsetX() / container.getGridBlockWidth();
            int offsetY = pressedShip.getDragOffsetY() / container.getGridBlockHeight();

            pressedShip.setLocation(point.x - offsetX, point.y - offsetY);

            if (board.checkForOverlappingShips()) {
                pressedShip.setLocation(oldX, oldY);
            }

            pressedShip = null;
        }
        
        container.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.printf("Dragging at x:%d y:%d\n", e.getX(), e.getY());

        if (pressedShip != null) {
            if (!pressedShip.isBeingDragged()) {
                int offsetX = e.getX() - pressedShip.getActualX();
                int offsetY = e.getY() - pressedShip.getActualY();
                pressedShip.setDragOffset(offsetX, offsetY);

                pressedShip.setIsBeingDragged(true);
            }

            pressedShip.setDragLocation(e.getX(), e.getY());
        }

        container.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = container.getGridCoordinateFrom(e);
        System.out.printf("Pointer is at x:%d y:%d\n", point.x, point.y);

        Ship ship = board.getShipAt(point.x, point.y);
        if (ship != null) {
            System.out.printf("Mouse moved over a ship (%s @ x:%d y:%d / %s)\n",
                    ship.getType(), ship.getX(), ship.getY(), new Date().toString());
        }

        container.repaint();
    }

}
