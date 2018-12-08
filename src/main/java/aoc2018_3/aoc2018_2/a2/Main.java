package aoc2018_3.aoc2018_2.a2;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI input = ClassLoader.getSystemResource("aoc2018_8/input.txt").toURI();

        List<String> ids = Files.lines(Paths.get(input)).sorted().collect(Collectors.toList());

        List<DiffResult> result = solve(ids);
        if (result.size() == 1) {
            String s = result.get(0).getStr1();
            int pos = result.get(0).getDiffPositions().get(0);
            System.out.println("Svar:" + s.substring(0, pos) + s.substring(pos + 1));
        } else {
            System.out.println("Konstigt resultat?!");
        }
    }

    private static List<DiffResult> solve(List<String> ids) {
        int idNr = 0;

        while (idNr < ids.size()) {
            String idStr = ids.get(idNr);
            List<DiffResult> match = ids.subList(idNr, ids.size())
                    .stream()
                    .map(otherStr -> diff(idStr, otherStr))
                    .filter(diffResult -> diffResult.getDiffPositions().size() == 1)
                    .collect(Collectors.toList());
            if (match.size() > 0) {
                return match;
            }
            idNr++;
        }
        return Collections.emptyList();
    }

    private static DiffResult diff(String str1, String str2) {
        DiffResult diffResult = new DiffResult(str1, str2);
        int pos = 0;

        while (pos < str1.length()) {
            if (str1.charAt(pos) != str2.charAt(pos)) {
                diffResult.addPosition(pos);
            }
            pos++;
        }
        return diffResult;
    }

    private static class DiffResult {
        String str1;
        String str2;
        List<Integer> diffPositions = Lists.newArrayList();

        private DiffResult(String str1, String str2) {
            this.str1 = str1;
            this.str2 = str2;
        }

        public String getStr1() {
            return str1;
        }

        public void setStr1(String str1) {
            this.str1 = str1;
        }

        public String getStr2() {
            return str2;
        }

        public void setStr2(String str2) {
            this.str2 = str2;
        }

        public List<Integer> getDiffPositions() {
            return diffPositions;
        }

        public void setDiffPositions(List<Integer> diffPositions) {
            this.diffPositions = diffPositions;
        }

        public void addPosition(Integer pos) {
            this.diffPositions.add(pos);
        }
    }
}
