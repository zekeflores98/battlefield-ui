package io.map.battleship.ui.game.board;

import io.map.battleship.ui.BattleshipGame;
import io.map.battleship.ui.game.markers.Marker;
import io.map.battleship.ui.game.ships.Ship;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {
    public static final int STANDBY_MODE = 0;
    public static final int GAME_MODE = 1;
    public static final int EDIT_MODE = 2;
    public static final int OWNER_MODE = 3;
    public static final int DEFAULT_DIVISIONS = 10;

    private final BoardContainer container;
    private final Map<String, Ship> ships;
    private final Map<String, Boolean> shipVisibility;
    private final List<Marker> markers;
    private final int maxX;
    private final int maxY;

    private int mode;
    private int prevMode;
    private BattleshipGame game;

    public Board() {
        this.maxX = DEFAULT_DIVISIONS;
        this.maxY = DEFAULT_DIVISIONS;
        this.container = new BoardContainer(this);

        markers = new ArrayList<>();
        ships = Ship.Collection.createSet(this);
        shipVisibility = Ship.Collection.createVisibilitySet();

        setMode(STANDBY_MODE);
    }

    public Board(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.container = new BoardContainer(this);

        markers = new ArrayList<>();
        ships = Ship.Collection.createSet(this);
        shipVisibility = Ship.Collection.createVisibilitySet();

        setMode(STANDBY_MODE);
    }

    public BoardContainer getContainer() {
        return container;
    }

    public BattleshipGame getGame() {
        return game;
    }
    
    public void setGame(BattleshipGame game) {
        this.game = game;
    }

    public int getPrevMode() {
        return prevMode;
    }

    public final int getMode() {
        return mode;
    }

    public final void setMode(int mode) {
        prevMode = this.mode;

        this.mode = mode;
        container.setMode(mode);

        switch (mode) {
            case Board.STANDBY_MODE: {
                if (prevMode == GAME_MODE) { // on pause
                    saveShipVisibility();
                } else {
                    setShipsVisibility(false);
                }
                break;
            }
            case Board.OWNER_MODE: { // board is owned by player. DNM/DC
                setShipsVisibility(true);
                break;
            }
            case Board.GAME_MODE: {
                if (prevMode == EDIT_MODE) { // on start
                    setShipsVisibility(false);
                } else if (prevMode == STANDBY_MODE) { // on resume
                    restoreShipVisibility();
                }
                break;
            }
            case Board.EDIT_MODE: {
                if (prevMode == STANDBY_MODE) { // on setup
                    setShipsVisibility(true);
                } else {
                    throw new IllegalStateException("Board can only be edited"
                            + " before the game is started.");
                }
                break;
            }
            default:
                throw new AssertionError();
        }
    }

    /**
     * Get the maximum zero-based index that is allowed to be used as the X
     * component of the coordinate of a ship.
     *
     * @return Non-zero-based index.
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Get the maximum zero-based index that is allowed to be used as the Y
     * component of the coordinate of a ship.
     *
     * @return Non-zero-based index.
     */
    public int getMaxY() {
        return maxY;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    public Marker getMarker(int x, int y) {
        throw new UnsupportedOperationException();
    }

    public void addMarker(Marker marker) {
        if (mode == EDIT_MODE) {
            return;
        }
        markers.add(marker);
    }

    public void removeMarker(Marker marker) {
        if (mode == EDIT_MODE) {
            return;
        }
        markers.add(marker);
    }

    public Map<String, Ship> getShips() {
        return ships;
    }

    public Ship getShip(String name) {
        return ships.get(name);
    }

    public Ship getShipAt(int x, int y) {
        Optional<Ship> resultShip = ships.values().stream().filter((ship) -> {
            switch (ship.getOrientation()) {
                case Ship.ORIENTATION_HORIZONTAL: {
                    if (ship.getY() == y && (ship.getX() <= x && x < ship.getX() + ship.getLength())) {
                        return true;
                    }
                    break;
                }
                case Ship.ORIENTATION_VERTICAL: {
                    if (ship.getX() == x && (ship.getY() <= y && y < ship.getY() + ship.getLength())) {
                        return true;
                    }
                    break;
                }
                default:
                    throw new AssertionError();
            }

            return false;
        }).findAny();

        return resultShip.orElse(null);
    }

    public void addShip(String name, Ship ship) {
        if (mode == GAME_MODE) {
            return;
        }
        ships.put(name, ship);
    }

    public void removeShip(String name) {
        if (mode == GAME_MODE) {
            return;
        }
        ships.remove(name);
    }

    public void setShipsVisibility(boolean isVisible) {
        for (Entry<String, Ship> entry : ships.entrySet()) {
            entry.getValue().setVisible(isVisible);
        }
    }

    public void saveShipVisibility() {
        for (Entry<String, Ship> entry : ships.entrySet()) {
            shipVisibility.put(entry.getKey(), entry.getValue().isVisible());
        }
    }

    public void restoreShipVisibility() {
        for (Entry<String, Boolean> entry : shipVisibility.entrySet()) {
            ships.get(entry.getKey()).setVisible(entry.getValue());
        }
    }

    public boolean isBlockOccupied(int x, int y) {
        return getShipAt(x, y) != null;
    }

    public boolean isBlockOccupied(Ship ship) {
        List<Ship> shipsAtLocation = getShipsOverlappingWith(ship);

        // check if there is a ship at coordinate x,y.
        // also check if it is the same ship at the location to
        // prevent the block from being marked as occupied even
        // if it's just itself.
        return (shipsAtLocation.size() > 1);
    }

    public List<Ship> getShipsOverlappingWith(Ship ship) {
        List<Point> points = ship.getOccupiedBlocks();
        List<Ship> resultShips = ships.values().stream().filter((s) -> {
            switch (s.getOrientation()) {
                case Ship.ORIENTATION_HORIZONTAL: {
                    for (Point point : points) {
                        if (s.getY() == point.y && (s.getX() <= point.x && point.x < s.getX() + s.getLength()))
                            return true;
                    }
                    break;
                }
                case Ship.ORIENTATION_VERTICAL: {
                    for (Point point : points) {
                        if (s.getX() == point.x && (s.getY() <= point.y && point.y < s.getY() + s.getLength()))
                            return true;
                    }
                    break;
                }
                default:
                    throw new AssertionError();
            }

            return false;
        }).collect(Collectors.toList());

        return resultShips;
    }
    
    public boolean checkForOverlappingShips() {
        boolean hasOverlappingShips = false;
        
        for (Ship ship : ships.values()) {
            for (Point point : ship.getOccupiedBlocks()) {
                boolean isOverlappingWithOtherShip = isBlockOccupied(ship);
                ship.setIsOverlapping(isOverlappingWithOtherShip);
                
                if (!hasOverlappingShips && isOverlappingWithOtherShip) {
                    hasOverlappingShips = true;
                    break;
                }
            }
        }
        
        return hasOverlappingShips;
    }
}
