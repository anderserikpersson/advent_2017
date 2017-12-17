package day17_2;


import java.io.IOException;

public class Main {

    private static final int MAX_MOVES = 50000000;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        System.out.println("Result=" + main.solve(335));
    }

    private Integer solve(int stepSize) throws IOException {
        Integer result = null;
        int pos = 1;
        int nrMoves = 1;
        int bufferSize = 1;

        while (nrMoves <= MAX_MOVES) {
            pos = (pos + stepSize) % bufferSize != 0 ? (pos + stepSize) % bufferSize + 1 : bufferSize + 1;

            if (pos == 2) {
                result = nrMoves;
            }
            nrMoves++;
            bufferSize++;
        }
        return result;
    }
}
