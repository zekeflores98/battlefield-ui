package io.map.battleship.ui.game;

import java.awt.Graphics;

public class HitMarker extends Marker {
    
    public HitMarker(Board board) {
        super(board);
        setType(Marker.HIT_MARKER);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }
}
