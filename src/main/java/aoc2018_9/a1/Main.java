package aoc2018_9.a1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;

public class Main {


    public static void main(String[] args) throws IOException {
        Main main = new Main();
        System.out.println("Resultat: " + main.solve(452, 70784).orElse(0));
    }

    private OptionalInt solve(int nrOfPlayers, int nrOfMarbles) {
        int currentMarble = 0;
        int currentMarblePos = 0;
        int currentPlayer = 0;
        ArrayList<Integer> board = new ArrayList<>();
        int[] scoreboard = new int[nrOfPlayers];
        board.add(0);

        while (currentMarble < nrOfMarbles) {
            currentMarble++;
            currentPlayer = (currentPlayer % nrOfPlayers) + 1;
            if (currentMarble % 23 == 0) {
                int removedMarblePos = currentMarblePos - 7 > 0 ? currentMarblePos - 7 : board.size() - 7 + currentMarblePos;
                currentMarblePos = removedMarblePos;
                scoreboard[currentPlayer - 1] += currentMarble + board.remove(removedMarblePos);
                System.out.println(currentMarble);
            } else {
                currentMarblePos = currentMarblePos + 2 > board.size() ? 1 : currentMarblePos + 2;
                board.add(currentMarblePos, currentMarble);
            }

/*            System.out.print("[" + currentPlayer + "] ");

            for (int index = 0; index < board.size(); index ++) {
                if (index == currentMarblePos) {
                    System.out.print("("+board.get(index)+ ") ");
                } else {
                    System.out.print(board.get(index)+ " ");
                }
            }
            System.out.println();*/
        }
        return Arrays.stream(scoreboard).max();
    }


}
