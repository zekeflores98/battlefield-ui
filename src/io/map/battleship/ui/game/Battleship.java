package io.map.battleship.ui.game;

import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Battleship extends Ship {

    public Battleship(Board board) {
        super(board, 4);
        type = Ship.BATTLESHIP;
        
        color = Color.MAGENTA;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}
