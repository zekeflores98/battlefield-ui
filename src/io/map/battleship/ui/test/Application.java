package io.map.battleship.ui.test;

import io.map.battleship.ui.game.board.Board;
import io.map.battleship.ui.game.ships.Ship;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        Player one = new Player() {
            @Override
            public Board getSecondaryBoard() {
                Board opponentPlayerBoard = new Board();
                opponentPlayerBoard.setMode(Board.STANDBY_MODE);

                Map<String, Ship> ships = opponentPlayerBoard.getShips();
                Ship ship = ships.get(Ship.AIRCRAFT_CARRIER); // 5
                ship.setOrientation(Ship.ORIENTATION_HORIZONTAL);
                ship.setLocation(2, 5);

                ship = ships.get(Ship.DESTROYER); // 2
                ship.setOrientation(Ship.ORIENTATION_VERTICAL);
                ship.setLocation(6, 0);

                ship = ships.get(Ship.CRUISER); // 3
                ship.setOrientation(Ship.ORIENTATION_HORIZONTAL);
                ship.setLocation(0, 3);

                ship = ships.get(Ship.SUBMARINE); // 3
                ship.setOrientation(Ship.ORIENTATION_VERTICAL);
                ship.setLocation(4, 7);

                ship = ships.get(Ship.BATTLESHIP); // 4
                ship.setOrientation(Ship.ORIENTATION_HORIZONTAL);
                ship.setLocation(6, 2);
                
                return opponentPlayerBoard;
            }
            
        };
        Player two = new Player();
        
        one.initialize();
        two.initialize();
        
        one.getGame().getController().setUpdateSender(two.getGame().getController().getUpdateReceiver());
        two.getGame().getController().setUpdateSender(one.getGame().getController().getUpdateReceiver());
    }
}
