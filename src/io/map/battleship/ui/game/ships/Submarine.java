package io.map.battleship.ui.game.ships;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.utils.ColorUtils;
import java.awt.Color;

public class Submarine extends Ship {

    public Submarine(Board board) {
        super(board, 3);
        type = Ship.SUBMARINE;
        
        color = Color.GREEN;
        dragColor = ColorUtils.makeTransparent(color, 0xAA);
    }
}
