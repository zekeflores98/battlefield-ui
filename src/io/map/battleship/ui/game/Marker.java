package io.map.battleship.ui.game;

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

    public void setType(int type) {
        this.type = type;
    }
    
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        int width = board.getContainer().getGridBlockWidth();
        int height = board.getContainer().getGridBlockHeight();
        int x = (this.x) * width;
        int y = (this.y) * height;
        
        if (type == HIT_MARKER) {
            g2.setPaint(Color.RED);
        } else if (type == MISS_MARKER) {
            g2.setPaint(Color.WHITE);
        } else {
            g2.setPaint(Color.PINK);
        }
        g2.fillRect(x, y, width, height);
    }
}
