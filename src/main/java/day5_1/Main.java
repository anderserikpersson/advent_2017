package day5_1;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

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
            pos = pos + instructions[pos-1]++;
            nrOfJumps++;
        }
        return nrOfJumps;
    }
}
