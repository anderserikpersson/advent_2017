package aoc2018_11.a1;

import com.google.common.base.Objects;

public class Main {

    public static final int CELLS_SIZE = 300;

    public static void main(String[] args) {
        Main main = new Main();
        main.solve(18);
    }

    private void solve(int gridSerialNr) {
        int[][] cells = buildCells(gridSerialNr);

        Coord maxPos = findSquareWithLargestSum(cells);
        System.out.println(maxPos);
    }

    private int[][] buildCells(int gridSerialNr) {
        int[][] cells = new int[CELLS_SIZE][CELLS_SIZE];

        for (int y = 1; y <= CELLS_SIZE; y++) {
            for (int x = 1; x <= CELLS_SIZE; x++) {
                int rackId = x + 10;
                int powerLevel = (rackId * y + gridSerialNr) * rackId;
                powerLevel = (powerLevel / 100) % 10;
                powerLevel = powerLevel - 5;
                cells[x - 1][y - 1] = powerLevel;
            }
        }
        return cells;
    }

    private Coord findSquareWithLargestSum(int[][] cells) {
        int maxValue = Integer.MIN_VALUE;
        Coord maxPos = new Coord();

        for (int y = 1; y <= CELLS_SIZE; y++) {
            System.out.println("Y=" + y);
            for (int x = 1; x <= CELLS_SIZE; x++) {
                int sum = 0;

                for (int dy = 0; dy < 3; dy++) {
                    for (int dx = 0; dx < 3; dx++) {
                        if (x + dx < CELLS_SIZE && y + dy < CELLS_SIZE) {
                            sum += cells[x + dx - 1][y + dy - 1];
                        }
                    }
                }
                if (sum > maxValue) {
                    maxValue = sum;
                    maxPos.setX(x);
                    maxPos.setY(y);
                }
            }
        }

        return maxPos;
    }

    private class Coord {
        int x;
        int Y;

        public Coord() {
        }

        public Coord(int x, int y) {
            this.x = x;
            Y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return Y;
        }

        public void setY(int y) {
            Y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return x == coord.x &&
                    Y == coord.Y;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, Y);
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "x=" + x +
                    ", Y=" + Y +
                    '}';
        }
    }

}
