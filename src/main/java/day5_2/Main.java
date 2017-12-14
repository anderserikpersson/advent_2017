package day5_2;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        URI input = ClassLoader.getSystemResource("day5/input.txt").toURI();
        int[] instructions = Files.lines(Paths.get(input)).mapToInt(Integer::parseInt).toArray();
        System.out.println("Result=" + main.solve(instructions));
    }

    private long solve(int[] instructions) {
        int pos = 1;
        long nrOfJumps = 0;

        while (pos <= instructions.length) {
            int offset = instructions[pos-1];
            if (offset >= 3) {
                instructions[pos-1]--;
            } else {
                instructions[pos-1]++;
            }
            pos = pos + offset;
            nrOfJumps++;
        }
        return nrOfJumps;
    }
}
