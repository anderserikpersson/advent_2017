package day17_1;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final int MAX_MOVES = 2017;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        System.out.println("Result=" + main.solve(335));
    }

    private int solve(int stepSize) throws IOException {
        List<Integer> buffer = new LinkedList<>();
        int pos = 1;
        int nrMoves = 0;
        buffer.add(pos - 1, nrMoves);

        while (nrMoves < MAX_MOVES) {
            pos = (pos + stepSize) % buffer.size() + 1;
            nrMoves++;
            buffer.add(pos, nrMoves);
        }
        return buffer.get(pos + 1);
    }
}
