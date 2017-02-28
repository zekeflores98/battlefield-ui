package io.map.battleship.ui.game.listeners.board;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.game.board.BoardContainer;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class BoardListener implements MouseListener, MouseMotionListener {
    
    protected final Board board;
    protected final BoardContainer container;
    
    public BoardListener(Board board, BoardContainer container) {
        this.board = board;
        this.container = container;
    }
}
