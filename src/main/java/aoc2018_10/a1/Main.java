package aoc2018_10.a1;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Main {

    public static final int MAX_DISTANCE = 3;

    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        main.solve("aoc2018_10/input.txt");
    }

    private void solve(String filePath) throws IOException, URISyntaxException {

        List<Star> stars = Lists.newArrayList();

        URI input = ClassLoader.getSystemResource(filePath).toURI();

        Files.lines(Paths.get(input)).forEach(line -> {
            int x = Integer.parseInt(line.split("position=<")[1].split(",")[0].trim());
            int y = Integer.parseInt(line.split("position=<")[1].split(",")[1].split(">")[0].trim());
            int vx = Integer.parseInt(line.split("velocity=<")[1].split(",")[0].trim());
            int vy = Integer.parseInt(line.split("velocity=<")[1].split(",")[1].split(">")[0].trim());
            stars.add(new Star(new Coord(x, y), new Coord(vx, vy)));
        });

        long time = 0;
        boolean found = allStarsAreClose(stars);
        List<Star> movedStars = stars;

        while (!found) {
            time++;
            movedStars = moveStars(movedStars);
            found = allStarsAreClose(movedStars);
        }

        System.out.println("-----------------------");
        System.out.println("Time -> " + time);
        printStars(movedStars);
        System.out.println("-----------------------");
    }

    private List<Star> moveStars(List<Star> stars) {
        return stars.stream().map(this::moveStar).collect(Collectors.toList());
    }

    private int distance(Coord pos1, Coord pos2) {
        return Math.abs(pos2.getX() - pos1.getX()) + Math.abs(pos2.getY() - pos1.getY());
    }

    private boolean allStarsAreClose(List<Star> stars) {
        return stars.stream().allMatch(star -> minDistanceToClosestStar(star, stars) < MAX_DISTANCE);
    }

    private int minDistanceToClosestStar(Star star, List<Star> stars) {
        return stars
                .stream()
                .mapToInt(s -> distance(star.getPos(), s.getPos()))
                .filter(d -> d > 0)
                .min().orElseThrow(NoSuchElementException::new);
    }

    private void printStars(List<Star> stars) {
        Coord minPos = minPos(stars);
        Coord maxPos = maxPos(stars);


        for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
            for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
                Coord currentPos = new Coord(x, y);
                if (stars.stream().anyMatch(star -> star.isAtPosition(currentPos))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }

            }
            System.out.println();
        }
    }

    private Star moveStar(Star star) {
        Coord newPos = new Coord(star.getPos().getX() + star.getVelocity().getX(), star.getPos().getY() + star.getVelocity().getY());
        return new Star(newPos, star.velocity);
    }

    private Coord maxPos(List<Star> stars) {
        int x = stars.stream().mapToInt(s -> s.getPos().getX()).max().orElse(0);
        int y = stars.stream().mapToInt(s -> s.getPos().getY()).max().orElse(0);
        return new Coord(x, y);
    }

    private Coord minPos(List<Star> stars) {
        int x = stars.stream().mapToInt(s -> s.getPos().getX()).min().orElse(0);
        int y = stars.stream().mapToInt(s -> s.getPos().getY()).min().orElse(0);
        return new Coord(x, y);
    }


    private class Star {
        Coord pos;
        Coord velocity;

        public Star(Coord pos, Coord velocity) {
            this.pos = pos;
            this.velocity = velocity;
        }

        public boolean isAtPosition(Coord pos) {
            return this.pos.getX() == pos.getX() && this.pos.getY() == pos.getY();
        }

        public Coord getPos() {
            return pos;
        }

        public void setPos(Coord pos) {
            this.pos = pos;
        }

        public Coord getVelocity() {
            return velocity;
        }

        public void setVelocity(Coord velocity) {
            this.velocity = velocity;
        }
    }

    private class Coord {
        int x;
        int Y;

        public Coord(int x, int y) {
            this.x = x;
            Y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return Y;
        }

        public void setY(int y) {
            Y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return x == coord.x &&
                    Y == coord.Y;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, Y);
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "x=" + x +
                    ", Y=" + Y +
                    '}';
        }
    }

}
