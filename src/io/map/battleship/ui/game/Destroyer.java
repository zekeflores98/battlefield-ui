package io.map.battleship.ui.game;

import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Destroyer extends Ship {

    public Destroyer(Board board) {
        super(board, 2);
        type = Ship.DESTROYER;
        
        color = Color.CYAN;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}
