package io.map.battleship.ui.game.ships;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;

public class Battleship extends Ship {

    public Battleship(Board board) {
        super(board, 4);
        type = Ship.BATTLESHIP;
        
        color = Color.MAGENTA;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}
