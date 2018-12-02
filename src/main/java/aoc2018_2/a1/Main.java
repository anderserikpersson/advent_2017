package aoc2018_2.a1;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI input = ClassLoader.getSystemResource("aoc2018_2/input.txt").toURI();

        long c2 = Files.lines(Paths.get(input)).filter(Main::containsLetterTwoTimes).count();
        long c3 = Files.lines(Paths.get(input)).filter(Main::containsLetterThreeTimes).count();
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c2 * c3);
    }

    private static boolean containsLetterTwoTimes(String str) {
        return containsLetter(str, 2L);
    }

    private static boolean containsLetterThreeTimes(String str) {
        return containsLetter(str, 3L);
    }

    private static boolean containsLetter(String str, Long nrOfTimes) {
        Map<String, Long> stats = Arrays.stream(str.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return stats.values().contains(nrOfTimes);
    }

}
