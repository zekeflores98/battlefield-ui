package io.map.battleship.ui.game;

import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cruiser extends Ship {

    public Cruiser(Board board) {
        super(board, 3);
        type = Ship.CRUISER;
        
        color = Color.ORANGE;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}
