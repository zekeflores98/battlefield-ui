package io.map.battleship.ui.game;

import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Submarine extends Ship {

    public Submarine(Board board) {
        super(board, 3);
        type = Ship.SUBMARINE;
        
        color = Color.GREEN;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}
