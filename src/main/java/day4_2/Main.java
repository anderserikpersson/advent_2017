package day4_2;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        System.out.println("Result=" + main.solve("day4/input.txt"));
    }

    private static boolean isValidPassphrase(String passphrase) {
        String[] words = passphrase.split(" ");
        Set<String> occupiedWords = new HashSet<>();
        for (String word : words) {
            for (String permutatedWord : permutation(word)) {
                if (occupiedWords.contains(permutatedWord)) {
                    return false;
                }
            }
            occupiedWords.add(word);
        }
        return true;
    }

    private static Set<String> permutation(String charpool) {
        Set<String> solutions = new HashSet<>();
        permutation("", charpool, solutions);
        return solutions;
    }

    private static void permutation(String prefix, String charpool, Set<String> solutions) {
        int n = charpool.length();
        if (n == 0) {
            solutions.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                permutation(prefix + charpool.charAt(i), charpool.substring(0, i) + charpool.substring(i + 1, n), solutions);
            }
        }
    }

    private long solve(String filePath) throws IOException {
        List<String> lines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(filePath));
        return lines.stream().filter(Main::isValidPassphrase).count();
    }

}
