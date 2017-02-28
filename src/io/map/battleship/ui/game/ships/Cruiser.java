package io.map.battleship.ui.game.ships;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;

public class Cruiser extends Ship {

    public Cruiser(Board board) {
        super(board, 3);
        type = Ship.CRUISER;
        
        color = Color.ORANGE;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}
