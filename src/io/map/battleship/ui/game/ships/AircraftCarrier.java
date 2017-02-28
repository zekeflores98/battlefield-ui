package io.map.battleship.ui.game.ships;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;

public class AircraftCarrier extends Ship {

    public AircraftCarrier(Board board) {
        super(board, 5);
        type = Ship.AIRCRAFT_CARRIER;
        
        color = Color.PINK;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}