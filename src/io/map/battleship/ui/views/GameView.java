package io.map.battleship.ui.views;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.game.board.BoardContainer;
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
    private JPanel primaryBoardContainer, secondaryBoardContainer;
    private JLabel lblPrimBoard;
    private JLabel lblSecBoard;
    private JLabel lblHeader;
    
    public GameView() {
        super();
        primaryBoard = new Board();
        secondaryBoard = new Board();
        
        initializeComponents();
    }
    
    public GameView(Board primaryBoard, Board secondaryBoard) {
        super(primaryBoard, secondaryBoard);
        
        initializeComponents();
    }
    
    public void setHeaderLabel(String text) {
        lblHeader.setText(text);
    }
    
    public void setPrimaryBoardLabel(String text) {
        lblPrimBoard.setText(text);
    }
    
    public void setSecondaryBoardLabel(String text) {
        lblSecBoard.setText(text);
    }

    @Override
    public int getType() {
        return BattleshipView.GAME_VIEW;
    }

    @Override
    public void setModeOfBoards() {
        if (primaryBoard != null) {
            primaryBoard.setMode(Board.GAME_MODE);
                
        }
        if (secondaryBoard != null) {
            secondaryBoard.setMode(Board.OWNER_MODE);
        }
    }

    private void initializeComponents() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        
        GridBagConstraints constr = new GridBagConstraints();
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.anchor = GridBagConstraints.NORTH;
        constr.weightx = 1.0;
        constr.weighty = 1.0;
        
        // game panel ======================================
        JPanel gamePanel = new JPanel();
        gamePanel.setOpaque(false);
        gamePanel.setLayout(new GridBagLayout());
        add(gamePanel);
        
        // primary board label =========================================
        lblPrimBoard = new JLabel("[LBL_PRIMARY_BOARD]"); 
        lblPrimBoard.setForeground(Color.WHITE);
        lblPrimBoard.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        Font lblPrimBoardFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
        lblPrimBoard.setFont(lblPrimBoardFont);
        
        constr.gridx = 0;
        constr.gridy = 0;
        gamePanel.add(lblPrimBoard, constr);
        
        // primary board ========================================
        primaryBoardContainer = new JPanel(new BorderLayout());
        primaryBoardContainer.setBackground(Color.GRAY);
        primaryBoardContainer.setPreferredSize(new Dimension(600, 600));
        
        constr.gridx = 0;
        constr.gridy = 1;
        gamePanel.add(primaryBoardContainer, constr);
        
        BoardContainer pbContainer = primaryBoard.getContainer();
        primaryBoardContainer.add(pbContainer, BorderLayout.CENTER);
        
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
        lblHeader = new JLabel("[LBL_HEADER]"); 
        lblHeader.setForeground(Color.WHITE);
        
        Font lblHeaderFont = new Font(Font.MONOSPACED, Font.BOLD, 24);
        lblHeader.setFont(lblHeaderFont);
        
        constr.gridx = 0;
        constr.gridy = 0;
        gameInfoStickyPanel.add(lblHeader, constr);
        
        // secondary board label ================================
        lblSecBoard = new JLabel("[LBL_SECONDARY_BOARD]"); 
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
