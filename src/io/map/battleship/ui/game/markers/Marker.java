package io.map.battleship.ui.game.markers;

import io.map.battleship.ui.game.board.Board;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Marker {
    
    public static final int HIT_MARKER = 1;
    public static final int MISS_MARKER = 2;
    
    private final Board board;
    private int type;
    private int x;
    private int y;
    
    public Marker(Board board) {
        this.board = board;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setLocation(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        int width = board.getContainer().getGridBlockWidth();
        int height = board.getContainer().getGridBlockHeight();
        int offsetWidth = board.getContainer().getGridBlockWidth() / 3;
        int offsetHeight = board.getContainer().getGridBlockHeight() / 3;
        int x = (this.x) * width;
        int y = (this.y) * height;
        int offsetX = x + offsetWidth;
        int offsetY = y + offsetHeight;
        
        if (type == HIT_MARKER) {
            g2.setPaint(Color.RED);
        } else if (type == MISS_MARKER) {
            g2.setPaint(Color.WHITE);
        } else {
            g2.setPaint(Color.PINK);
        }
        g2.fillRect(offsetX, offsetY, offsetWidth, offsetHeight);
    }
}
