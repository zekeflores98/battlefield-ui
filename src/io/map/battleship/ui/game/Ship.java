package io.map.battleship.ui.game;

import io.map.battleship.ui.Globals;
import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Ship {
    public static final String AIRCRAFT_CARRIER = "aircraft_carrier";
    public static final String BATTLESHIP = "battleship";
    public static final String CRUISER = "cruiser";
    public static final String SUBMARINE = "submarine";
    public static final String DESTROYER = "destroyer";
    
    public static final int ORIENTATION_VERTICAL = 1;
    public static final int ORIENTATION_HORIZONTAL = 2;
    
    protected final Board board;
    protected final int length;
    
    protected Color dragColor;
    protected Color color;
    
    protected boolean isBeingDragged;
    protected int dragOffsetX;
    protected int dragOffsetY;
    protected int dragX;
    protected int dragY;
    
    protected boolean isOverlapping;
    protected boolean isVisible;
    protected int orientation;
    protected String type;
    protected int x;
    protected int y;
    
    public Ship(Board board, int length) {
        this.board = board;
        this.length = length;
        
        isVisible = false;
        orientation = ORIENTATION_VERTICAL;
        
        color = Color.YELLOW;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public boolean isBeingDragged() {
        return isBeingDragged;
    }

    public void setIsBeingDragged(boolean isBeingDragged) {
        this.isBeingDragged = isBeingDragged;
    }

    public boolean isOverlapping() {
        return isOverlapping;
    }

    public void setIsOverlapping(boolean isOverlapping) {
        this.isOverlapping = isOverlapping;
    }

    public boolean isVisible() {
        return isVisible;
    }
    
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        
        if (orientation == ORIENTATION_HORIZONTAL) {
            if ((this.x + length) > board.getMaxX()) {
                this.x = board.getMaxX() - length;
            }
        } else if (orientation == ORIENTATION_VERTICAL) {
            if ((this.x + 1) > board.getMaxX()) {
                this.x = board.getMaxX() - 1;
            }
        } else {
            throw new AssertionError();
        }
    }

    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
        
        if (orientation == ORIENTATION_HORIZONTAL) {
            if ((this.x + length) > board.getMaxX()) {
                this.x = board.getMaxX() - length;
            }
        } else if (orientation == ORIENTATION_VERTICAL) {
            if ((this.x + 1) > board.getMaxX()) {
                this.x = board.getMaxX() - 1;
            }
        } else {
            throw new AssertionError();
        }
        
        if (this.x < 0) {
            this.x = 0;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        
        if (orientation == ORIENTATION_HORIZONTAL) {
            if ((this.y + 1) > board.getMaxY()) {
                this.y = board.getMaxY() - 1;
            }
        } else if (orientation == ORIENTATION_VERTICAL) {
            if ((this.y + length) > board.getMaxY()) {
                this.y = board.getMaxY() - length;
            }
        } else {
            throw new AssertionError();
        }
        
        if (this.y < 0) {
            this.y = 0;
        }
    }
    
    public int getActualX() {
        return this.x * board.getContainer().getGridBlockWidth();
    }
    
    public int getActualY() {
        return this.y * board.getContainer().getGridBlockHeight();
    }
    
    public void setLocation(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getDragX() {
        return dragX;
    }
    
    public void setDragX(int dragX) {
        this.dragX = dragX;
    }

    public int getDragY() {
        return dragY;
    }
    
    public void setDragY(int dragY) {
        this.dragY = dragY;
    }
    
    public void setDragLocation(int x, int y) {
        setDragX(x);
        setDragY(y);
    }

    public int getDragOffsetX() {
        return dragOffsetX;
    }

    public void setDragOffsetX(int dragOffsetX) {
        this.dragOffsetX = dragOffsetX;
    }
    
    public int getDragOffsetY() {
        return dragOffsetY;
    }

    public void setDragOffsetY(int dragOffsetY) {
        this.dragOffsetY = dragOffsetY;
    }
    
    public void setDragOffset(int x, int y) {
        setDragOffsetX(x);
        setDragOffsetY(y);
    }
    
    public List<Point> getOccupiedBlocks() {
        List<Point> occupiedBlocks = new ArrayList<>();
        
        if (orientation == ORIENTATION_HORIZONTAL) {
            for (int i = this.x; i < this.x + length; i++) {
                occupiedBlocks.add(new Point(i, y));
            }
        } else if (orientation == ORIENTATION_VERTICAL) {
            for (int i = this.y; i < this.y + length; i++) {
                occupiedBlocks.add(new Point(x, i));
            }
        } else {
            throw new AssertionError();
        }
        
        return occupiedBlocks;
    }
    
    public boolean isHit(int x, int y) {
        if (orientation == ORIENTATION_HORIZONTAL) {
            if (this.x <= x && x < (this.x + length)) {
                return true;
            }
        } else if (orientation == ORIENTATION_VERTICAL) {
            if (this.y <= y && y < (this.y + length)) {
                return true;
            }
        } else {
            throw new AssertionError();
        }
        
        return false;
    }
    
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(color);
        
        if (!isVisible) {
            return;
        }
        
        int width = board.getContainer().getGridBlockWidth();
        int height = board.getContainer().getGridBlockHeight();
        int x = (this.x) * width;
        int y = (this.y) * height;
        
        if (orientation == ORIENTATION_HORIZONTAL) {    
            g.fillRect(x, y, length * width, height);
            
            if (isBeingDragged) {
                g2.setPaint(dragColor);
                g.fillRect(dragX - dragOffsetX, dragY - dragOffsetY, length * width, height);
                g2.setPaint(color);
            }
            if (isOverlapping) {
                g2.setPaint(Globals.HIT_COLOR);
                g.drawRect(x, y, length * width, height);
                g2.setPaint(color);
            }
        } else if (orientation == ORIENTATION_VERTICAL) {
            g.fillRect(x, y, width, length * height);
            
            if (isBeingDragged) {
                g2.setPaint(dragColor);
                g.fillRect(dragX - dragOffsetX, dragY - dragOffsetY, width, length * height);
                g2.setPaint(color);
            }
            if (isOverlapping) {
                g2.setPaint(Globals.HIT_COLOR);
                g.drawRect(x, y, width, length * height);
                g2.setPaint(color);
            }
        } else {
            throw new AssertionError();
        }
    }
    
    public static class Collection {
        public static Map<String,Ship> createSet(Board board) {
            HashMap<String,Ship> ships = new HashMap<>();
            
            Ship ac = new AircraftCarrier(board);
            ac.orientation = ORIENTATION_VERTICAL;
            ac.x = 0;
            ac.y = 0;
            ships.put(AIRCRAFT_CARRIER, ac);
            
            ac = new Battleship(board);
            ac.orientation = ORIENTATION_VERTICAL;
            ac.x = 1;
            ac.y = 0;
            ships.put(BATTLESHIP, ac);
            
            ac = new Submarine(board);
            ac.orientation = ORIENTATION_VERTICAL;
            ac.x = 2;
            ac.y = 0;
            ships.put(SUBMARINE, ac);
            
            ac = new Cruiser(board);
            ac.orientation = ORIENTATION_VERTICAL;
            ac.x = 3;
            ac.y = 0;
            ships.put(CRUISER, ac);
            
            ac = new Destroyer(board);
            ac.orientation = ORIENTATION_VERTICAL;
            ac.x = 4;
            ac.y = 0;
            ships.put(DESTROYER, ac);
            
            return ships;
        }
        
        public static Map<String, Boolean> createVisibilitySet() {
            HashMap<String,Boolean> ships = new HashMap<>();
            
            ships.put(AIRCRAFT_CARRIER, false);
            ships.put(BATTLESHIP, false);
            ships.put(SUBMARINE, false);
            ships.put(CRUISER, false);
            ships.put(DESTROYER, false);
            
            return ships;
        }
    }
}
