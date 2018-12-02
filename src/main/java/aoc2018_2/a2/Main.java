package aoc2018_2.a2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI input = ClassLoader.getSystemResource("aoc2018_2/input.txt").toURI();

        List<String> ids = Files.lines(Paths.get(input)).sorted().collect(Collectors.toList());

        System.out.println(solve(ids));

    }

    private static Optional<String> solve(List<String> ids) {
        int idNr = 0;

        while (idNr < ids.size()) {
            String idStr = ids.get(idNr);
            List<String> match = ids.subList(idNr, ids.size()).stream().filter(otherStr -> diffOneChar(idStr, otherStr)).collect(Collectors.toList());
            if (match.size() > 0) {
                match.forEach(System.out::println);
                return Optional.of(idStr);
            }
            idNr++;
        }
        return Optional.empty();
    }

    private static boolean diffOneChar(String str1, String str2) {
        int diffCounter = 0;
        int pos = 0;
        while (pos < str1.length() && diffCounter <= 1) {
            if (str1.charAt(pos) != str2.charAt(pos)) {
                diffCounter++;
            }
            pos++;
        }
        return diffCounter == 1;
    }

    private class DiffResult {
        String str1;
        String str2;
        int pos;

        public DiffResult(String str1, String str2, int pos) {
            this.str1 = str1;
            this.str2 = str2;
            this.pos = pos;
        }
    }

}
