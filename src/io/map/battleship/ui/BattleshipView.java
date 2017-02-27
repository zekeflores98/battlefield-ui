package io.map.battleship.ui;

import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class BattleshipView extends JPanel {
    
    public static final int GAME_VIEW = 1;
    public static final int PRE_GAME_VIEW = 2;
    public static final int POST_GAME_VIEW = 3;

    private BattleshipWindow window;
    
    public BattleshipView() {
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    }
    
    public void setParentWindow(BattleshipWindow window) {
        this.window = window;
    }
}
