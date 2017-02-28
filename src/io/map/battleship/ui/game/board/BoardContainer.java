package io.map.battleship.ui.game.board;

import io.map.battleship.ui.game.markers.Marker;
import io.map.battleship.ui.game.ships.Ship;
import io.map.battleship.ui.utils.Globals;
import io.map.battleship.ui.game.listeners.board.BoardListener;
import io.map.battleship.ui.game.listeners.board.EditBoardListener;
import io.map.battleship.ui.game.listeners.board.GameBoardListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BoardContainer extends JPanel {
    private final Board board;
    private final BoardListener currentBoardListener;
    private final BoardListener editBoardListener;
    private final BoardListener gameBoardListener;
    
    public BoardContainer(Board board) {
        this.board = board;
        
        currentBoardListener = null;
        editBoardListener = new EditBoardListener(board, this);
        gameBoardListener = new GameBoardListener(board, this);
        
        initializeComponents();
    }

    public void setMode(int mode) {
        if (currentBoardListener != null) {
            removeMouseListener(currentBoardListener);
            removeMouseMotionListener(currentBoardListener);
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
                addMouseListener(gameBoardListener);
                addMouseMotionListener(gameBoardListener);
                break;
            }
            case Board.EDIT_MODE: {
                setBackground(Globals.PRIMARY_COLOR);
                addMouseListener(editBoardListener);
                addMouseMotionListener(editBoardListener);
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
    
    public Point getGridCoordinateFrom(MouseEvent e) {
        Point point = new Point();
        
        int subdivisionHeight = getGridBlockHeight();
        int subdivisionWidth = getGridBlockWidth();
        
        point.x = e.getX() / subdivisionWidth;
        point.y = e.getY() / subdivisionHeight;
        
        return point;
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
}
