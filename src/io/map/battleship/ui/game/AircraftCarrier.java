package io.map.battleship.ui.game;

import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class AircraftCarrier extends Ship {

    public AircraftCarrier(Board board) {
        super(board, 5);
        type = Ship.AIRCRAFT_CARRIER;
        
        color = Color.PINK;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}