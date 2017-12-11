package day3_1;

/**
 * Created by anders.erik.persson@gmail.com on 2017-12-03.
 */
public class Main {

    final private Coord EAST = new Coord(1, 0);
    final private Coord NORTH = new Coord(0, 1);
    final private Coord WEST = new Coord(-1, 0);
    final private Coord SOUTH = new Coord(0, -1);

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Result:" + main.solve(1024));
    }

    private int solve(int targetCellNumber) {
        int cellNumber = 1;
        Coord pos = new Coord(0, 0);
        Coord direction = EAST;
        int boxSize = 1;
        int maxCoord = 0;

        while (cellNumber < targetCellNumber) {
            cellNumber++;

            if (couldMove(pos, direction, maxCoord)) {
                pos = move(pos, direction);
            } else {
                direction = rotateAntiClockwise(direction);
                pos = move(pos, direction);
            }

            if (shouldExpandBox(cellNumber, boxSize)) {
                boxSize++;
                maxCoord = (int) (Math.floor((boxSize)) / 2);
            }
        }
        return Math.abs(pos.getX()) + Math.abs(pos.getY());
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
    }
}
