package aoc2018_11.a2;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

public class Main {

    private static final int CELLS_SIZE = 300;

    private static int counter = 0;

    public static void main(String[] args) {
        Main main = new Main();
        main.solve(3999);
    }

    private void solve(int gridSerialNr) {
        Integer[] cells = buildCells(gridSerialNr);

        Optional<Result> optRes = IntStream
                .range(0, cells.length)
                .mapToObj(idx -> maxSumSquareAllSquares(cells, idx))
                .parallel()
                .max(Comparator.comparing(Result::getSum));

        if (optRes.isPresent()) {
            Result res = optRes.get();
            int x = 1 + res.getIndex() % CELLS_SIZE;
            int y = 1 + res.getIndex() / CELLS_SIZE;
            System.out.println("Sum:" + res.getSum());
            System.out.println("x:" + x);
            System.out.println("y:" + y);
            System.out.println("Size:" + res.getSize());
        }
    }


    private Result maxSumSquareAllSquares(Integer[] cells, int start) {
        Result result = null;
        int maxSum = Integer.MIN_VALUE;
        for (int size = 1; size <= 300; size++) {
            int sum = sumSquare(cells, start, size);
            if (sum > maxSum) {
                maxSum = sum;
                result = new Result(sum, start, size);
            }
        }
        counter++;
        if (counter % 1000 == 0) System.out.println(counter);
        return result;
    }


    private int sumSquare(Integer[] cells, int start, int size) {

        int sum = 0;
        for (int dy = 0; dy < size; dy++) {
            for (int dx = 0; dx < size; dx++) {
                int index = start + dx + (dy * CELLS_SIZE);
                if (index < cells.length) {
                    sum += cells[index];
                }
            }
        }
        return sum;
    }

    private Integer[] buildCells(int gridSerialNr) {
        Integer[] cells = new Integer[CELLS_SIZE * CELLS_SIZE];

        for (int y = 1; y <= CELLS_SIZE; y++) {
            for (int x = 1; x <= CELLS_SIZE; x++) {
                int rackId = x + 10;
                int powerLevel = (rackId * y + gridSerialNr) * rackId;
                powerLevel = (powerLevel / 100) % 10;
                powerLevel = powerLevel - 5;
                int index = (y - 1) * CELLS_SIZE + (x - 1);
                cells[index] = powerLevel;
            }
        }
        return cells;
    }

    private class Result {
        int sum;
        int index;
        int size;

        public Result(int sum, int index, int size) {
            this.sum = sum;
            this.index = index;
            this.size = size;
        }

        public int getSum() {
            return sum;
        }

        public int getIndex() {
            return index;
        }

        public int getSize() {
            return size;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "sum=" + sum +
                    ", index=" + index +
                    ", size=" + size +
                    '}';
        }
    }
}

