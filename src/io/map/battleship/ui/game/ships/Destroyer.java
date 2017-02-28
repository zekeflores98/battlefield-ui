package io.map.battleship.ui.game.ships;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;

public class Destroyer extends Ship {

    public Destroyer(Board board) {
        super(board, 2);
        type = Ship.DESTROYER;
        
        color = Color.CYAN;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}
