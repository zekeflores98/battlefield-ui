package io.map.battleship.ui.game;

import io.map.battleship.ui.Globals;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BoardContainer extends JPanel {
    private final Board board;
    private final MouseListener currentMouseListener;
    private final MouseListener editMouseListener;
    private final MouseListener gameMouseListener;
    private final MouseMotionListener currentMouseMotionListener;
    private final MouseMotionListener editMouseMotionListener;
    private final MouseMotionListener gameMouseMotionListener;
    
    public BoardContainer(Board board) {
        this.board = board;
        
        currentMouseListener = null;
        currentMouseMotionListener = null;
        editMouseListener = new EditMouseListener();
        editMouseMotionListener = new EditMouseMotionListener();
        gameMouseListener = new GameMouseListener();
        gameMouseMotionListener = new GameMouseMotionListener();
        
        initializeComponents();
    }

    public void setMode(int mode) {
        if (currentMouseListener != null) {
            removeMouseListener(currentMouseListener);
        }
        if (currentMouseMotionListener != null) {
            removeMouseMotionListener(currentMouseMotionListener);
        }
        
        switch (mode) {
            case Board.STANDBY_MODE: {
                setBackground(Globals.DISABLED_COLOR);
                break;
            }
            case Board.OWNER_MODE: {
                setBackground(Globals.DISABLED_COLOR);
                break;
            }
            case Board.GAME_MODE: {
                setBackground(Globals.PRIMARY_COLOR);
                addMouseListener(gameMouseListener);
                addMouseMotionListener(gameMouseMotionListener);
                break;
            }
            case Board.EDIT_MODE: {
                setBackground(Globals.PRIMARY_COLOR);
                addMouseListener(editMouseListener);
                addMouseMotionListener(editMouseMotionListener);
                break;
            }
            default:
                throw new AssertionError();
        }
    }
    
    public int getGridBlockHeight() {
        return getHeight() / board.getMaxY();
    }

    public int getGridBlockWidth() {
        return getWidth() / board.getMaxX();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int subdivisionHeight = getGridBlockHeight();
        int subdivisionWidth = getGridBlockWidth();
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Globals.SECONDARY_BORDER_COLOR);
        
        for (int i=1; i <= board.getMaxX(); i++) {
            int x = i * subdivisionHeight;
            g2.drawLine(x, 0, x, getWidth());
        }
        
        for (int i=1; i <= board.getMaxY(); i++) {
            int y = i * subdivisionWidth;
            g2.drawLine(0, y, getWidth(), y);
        }
        
        for (Map.Entry<String, Ship> entry : board.getShips().entrySet()) {
            entry.getValue().render(g);
        }
        
        for (Marker marker : board.getMarkers()) {
            marker.render(g);
        }
    }
    
    private void initializeComponents() {
        setBorder(BorderFactory.createLineBorder(Globals.PRIMARY_BORDER_COLOR, 1));
        setBackground(Globals.PRIMARY_COLOR);
    }
    
    private Point getGridCoordinateFrom(MouseEvent e) {
        Point point = new Point();
        
        int subdivisionHeight = getGridBlockHeight();
        int subdivisionWidth = getGridBlockWidth();
        
        point.x = e.getX() / subdivisionWidth;
        point.y = e.getY() / subdivisionHeight;
        
        return point;
    }
    
    private Ship pressedShip;
    
    private class EditMouseMotionListener implements MouseMotionListener {

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
            
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point point = getGridCoordinateFrom(e);
            System.out.printf("Pointer is at x:%d y:%d\n", point.x, point.y);
            
            Ship ship = board.getShipAt(point.x, point.y);
            if (ship != null) {
                System.out.printf("Mouse moved over a ship (%s @ x:%d y:%d / %s)\n", 
                        ship.getType(), ship.getX(), ship.getY(), new Date().toString());
            }
            
            repaint();
        }
    }
    
    private class GameMouseMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
        
    }
    
    private class EditMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.printf("Clicked at x:%d y:%d\n", e.getX(), e.getY());
            Point point = getGridCoordinateFrom(e);
            
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
            
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.printf("Pressed at x:%d y:%d\n", e.getX(), e.getY());
            Point point = getGridCoordinateFrom(e);
            
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
                
                Point point = getGridCoordinateFrom(e);
                int offsetX = pressedShip.getDragOffsetX() / getGridBlockWidth();
                int offsetY = pressedShip.getDragOffsetY() / getGridBlockHeight();
                
                pressedShip.setLocation(point.x - offsetX, point.y - offsetY);
                
                if (board.checkForOverlappingShips()) {
                    pressedShip.setLocation(oldX, oldY);
                }
                
                pressedShip = null;
            }
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    
    private class GameMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
}
