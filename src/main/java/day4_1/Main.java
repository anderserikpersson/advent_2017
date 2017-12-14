package day4_1;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        System.out.println("Result=" + main.solve("day4/input.txt"));
    }

    private long solve(String filePath) throws IOException {
        List<String> lines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(filePath));
        return lines.stream().filter(Main::isValidPassphrase).count();
    }

    private static boolean isValidPassphrase(String passphrase) {
        Stream<String> words = Arrays.stream(passphrase.split(" "));
        return words.allMatch(new HashSet<>()::add);
    }
}
