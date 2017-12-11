package day3_2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anders.erik.persson@gmail.com on 2017-12-03.
 */
public class Main {

    final private Coord EAST = new Coord(1, 0);
    final private Coord NORTH = new Coord(0, 1);
    final private Coord WEST = new Coord(-1, 0);
    final private Coord SOUTH = new Coord(0, -1);
    final private Coord NORTH_EAST = new Coord(1, 1);
    final private Coord NORTH_WEST = new Coord(-1, 1);
    final private Coord SOUTH_EAST = new Coord(1, -1);
    final private Coord SOUTH_WEST = new Coord(-1, -1);

    private Map<Coord, Integer> cellValues = new HashMap<Coord, Integer>();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Result:" + main.solve(277678));
    }

    private int solve(int targetCellValue) {
        int cellNumber = 1;
        Coord pos = new Coord(0, 0);
        Coord direction = EAST;
        int boxSize = 1;
        int maxCoord = 0;
        int currentCellValue = 1;
        cellValues.put(pos, currentCellValue);

        while (currentCellValue < targetCellValue) {
            cellNumber++;

            if (couldMove(pos, direction, maxCoord)) {
                pos = move(pos, direction);
            } else {
                direction = rotateAntiClockwise(direction);
                pos = move(pos, direction);
            }
            currentCellValue = calculateValue(pos);
            cellValues.put(pos, currentCellValue);

            if (shouldExpandBox(cellNumber, boxSize)) {
                boxSize++;
                maxCoord = (int) (Math.floor((boxSize)) / 2);
            }
        }
        return currentCellValue;
    }

    private int calculateValue(Coord pos) {
        return getValue(move(pos, NORTH)) +
                getValue(move(pos, EAST)) +
                getValue(move(pos, WEST)) +
                getValue(move(pos, SOUTH)) +
                getValue(move(pos, NORTH_EAST)) +
                getValue(move(pos, NORTH_WEST)) +
                getValue(move(pos, SOUTH_EAST)) +
                getValue(move(pos, SOUTH_WEST));
    }

    private int getValue(Coord pos) {
        Integer value = cellValues.get(pos);
        return value != null ? value : 0;
    }

    private Coord rotateAntiClockwise(Coord direction) {
        if (direction.equals(EAST)) return NORTH;
        if (direction.equals(NORTH)) return WEST;
        if (direction.equals(WEST)) return SOUTH;
        if (direction.equals(SOUTH)) return EAST;
        throw new IllegalArgumentException("Invalid direction " + direction);
    }

    private boolean shouldExpandBox(int cellNumber, int boxSize) {
        return cellNumber >= boxSize * boxSize;
    }

    private Coord move(Coord pos, Coord direction) {
        return new Coord(pos.getX() + direction.getX(), pos.getY() + direction.getY());
    }

    private boolean couldMove(Coord pos, Coord direction, int maxCoord) {
        Coord newPos = move(pos, direction);
        return Math.abs(newPos.getX()) <= maxCoord && Math.abs(newPos.getY()) <= maxCoord;
    }

    private class Coord {
        int x, y;

        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        void setX(int x) {
            this.x = x;
        }

        int getY() {
            return y;
        }

        void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coord coord = (Coord) o;

            if (x != coord.x) return false;
            return y == coord.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
