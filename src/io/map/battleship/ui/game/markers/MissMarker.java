package io.map.battleship.ui.game.markers;

import io.map.battleship.ui.game.board.Board;
import java.awt.Graphics;

public class MissMarker extends Marker {
    
    public MissMarker(Board board) {
        super(board);
        setType(Marker.MISS_MARKER);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }
}
