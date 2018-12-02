package aoc2018_1;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI input = ClassLoader.getSystemResource("aoc2018_1/input.txt").toURI();
        int[] changes = Files.lines(Paths.get(input)).mapToInt(Integer::parseInt).toArray();

        System.out.println(solve(changes).orNull());
    }

    private static Optional<Integer> solve(int[] changes) {
        Integer currentFreq = 0;
        Integer newFreq = 0;
        Set<Integer> prevFreqences = Sets.newHashSet(currentFreq);

        while (true) {
            for (int change : changes) {
                newFreq = currentFreq + change;
                if (prevFreqences.contains(newFreq)) {
                    return Optional.of(newFreq);
                } else {
                    currentFreq = newFreq;
                    prevFreqences.add(currentFreq);
                }
            }
        }
    }

}
