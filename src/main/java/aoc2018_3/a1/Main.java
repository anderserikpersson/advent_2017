package aoc2018_3.a1;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {


    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        System.out.println("Result=" + main.solve("aoc2018_3/input.txt"));
    }

    private long solve(String filePath) throws IOException, URISyntaxException {
        Fabric fabric = new Fabric();

        URI input = ClassLoader.getSystemResource(filePath).toURI();
        Files.lines(Paths.get(input)).map(Claim::new).forEach((fabric::claim));


        return fabric.countOverclaimed();
    }

    private class Fabric {
        private int[][] fabric;

        Fabric() {
            fabric = new int[1000][1000];
        }

        void claim(Claim claim) {
            for (int x = claim.x; x < claim.x + claim.wide; x++) {
                for (int y = claim.y; y < claim.y + claim.tall; y++) {
                    fabric[x][y]++;
                }
            }
        }

        public int countOverclaimed() {
            int overclaimed = 0;

            for (int x = 0; x < fabric.length; x++) {
                for (int y = 0; y < fabric.length; y++) {
                    if (fabric[x][y] > 1) {
                        overclaimed++;
                    }
                }
            }
            return overclaimed;
        }
    }

    private class Claim {
        int id;
        int x;
        int y;
        int wide;
        int tall;

        public Claim(String line) {
            String[] parts = line.split(" ");
            this.id = Integer.parseInt(parts[0].substring(1));
            this.x = Integer.parseInt(parts[2].split(",")[0]);
            this.y = Integer.parseInt(parts[2].split(",")[1].split(":")[0]);
            this.wide = Integer.parseInt(parts[3].split("x")[0]);
            this.tall = Integer.parseInt(parts[3].split("x")[1]);
        }

        @Override
        public String toString() {
            return "ReposeRecord{" +
                    "id=" + id +
                    ", x=" + x +
                    ", y=" + y +
                    ", wide=" + wide +
                    ", tall=" + tall +
                    '}';
        }
    }
}
