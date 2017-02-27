package io.map.battleship.ui.views;

import io.map.battleship.ui.BattleshipView;
import io.map.battleship.ui.BattleshipWindow;
import io.map.battleship.ui.game.Board;
import io.map.battleship.ui.game.BoardContainer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * View to show while the game is in progress.
 * 
 * @author bmdelacruz
 */
public class GameView extends BattleshipView {
    
    private final BattleshipWindow window;
    private Board primaryBoard, secondaryBoard;
    private JPanel primaryBoardContainer, secondaryBoardContainer;
    
    // TODO: Create a panel to be set with a preferred size instead of
    // setting the preferred size of the board.
    // TODO: Switch boards
    
    public GameView(BattleshipWindow window) {
        super();
        this.window = window;
        this.primaryBoard = new Board();
        this.secondaryBoard = new Board();
        
        initializeComponents(window);
    }
    
    public GameView(BattleshipWindow window, Board primaryBoard, Board secondaryBoard) {
        super();
        this.window = window;
        this.primaryBoard = primaryBoard;
        this.secondaryBoard = secondaryBoard;
        
        initializeComponents(window);
    }

    public Board getPrimaryBoard() {
        return primaryBoard;
    }

    public Board getSecondaryBoard() {
        return secondaryBoard;
    }
    
    public void switchBoards() {
        BoardContainer pbc = (BoardContainer) primaryBoardContainer.getComponent(0);
        BoardContainer sbc = (BoardContainer) secondaryBoardContainer.getComponent(0);
        
        primaryBoardContainer.add(sbc);
        secondaryBoardContainer.add(pbc);
    }

    private void initializeComponents(BattleshipWindow window) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setParentWindow(window);
        setOpaque(false);
        
        primaryBoardContainer = new JPanel(new BorderLayout());
        primaryBoardContainer.setBackground(Color.GRAY);
        primaryBoardContainer.setPreferredSize(new Dimension(600, 600));
        add(primaryBoardContainer);
        
        // primary board ========================================
        BoardContainer pbContainer = primaryBoard.getContainer();
        primaryBoardContainer.add(pbContainer, BorderLayout.CENTER);
        
        GridBagConstraints constr = new GridBagConstraints();
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.anchor = GridBagConstraints.NORTH;
        constr.weightx = 1.0;
        constr.weighty = 1.0;
        
        // game info panel ======================================
        JPanel gameInfoPanel = new JPanel();
        gameInfoPanel.setOpaque(false);
        gameInfoPanel.setLayout(new GridBagLayout());
        gameInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        add(gameInfoPanel, BorderLayout.PAGE_START);
        
        // game info sticky panel ======================================
        JPanel gameInfoStickyPanel = new JPanel();
        gameInfoStickyPanel.setOpaque(false);
        gameInfoStickyPanel.setLayout(new GridBagLayout());
        gameInfoPanel.add(gameInfoStickyPanel, constr);
        
        // header label =========================================
        JLabel lblHeader = new JLabel("Battleship"); 
        lblHeader.setForeground(Color.WHITE);
        
        Font lblHeaderFont = new Font(Font.MONOSPACED, Font.BOLD, 24);
        lblHeader.setFont(lblHeaderFont);
        
        constr.gridx = 0;
        constr.gridy = 0;
        gameInfoStickyPanel.add(lblHeader, constr);
        
        // secondary board label ================================
        JLabel lblSecBoard = new JLabel("Your fleet"); 
        lblSecBoard.setForeground(Color.WHITE);
        lblSecBoard.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        
        Font lblSecBoardFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
        lblSecBoard.setFont(lblSecBoardFont);
        
        constr.gridx = 0;
        constr.gridy = 1;
        gameInfoStickyPanel.add(lblSecBoard, constr);
        
        secondaryBoardContainer = new JPanel(new BorderLayout());
        secondaryBoardContainer.setPreferredSize(new Dimension(200, 200));
        
        // secondary board ======================================
        BoardContainer sbContainer = secondaryBoard.getContainer();
        secondaryBoardContainer.add(sbContainer, BorderLayout.CENTER);
        
        constr.gridx = 0;
        constr.gridy = 2;
        gameInfoStickyPanel.add(secondaryBoardContainer, constr);
    }
}
