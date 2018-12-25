package aoc2018_25.a1;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        System.out.println("Result=" + main.solve("aoc2018_25/input.txt"));
    }

    private long solve(String filePath) throws IOException, URISyntaxException {
        URI input = ClassLoader.getSystemResource(filePath).toURI();
        List<Point> points = Files.lines(Paths.get(input)).map(Point::new).collect(Collectors.toList());
        ArrayList<Constellation> constellations = Lists.newArrayList();

        points.forEach(point -> {
            System.out.println("----: Kollar : " + point + " :---------");
            Optional<Constellation> constellation = constellations.stream().filter(c -> c.isMember(point)).findFirst();

            if (constellation.isPresent()) {
                System.out.println("Join");
                constellation.get().join(point);
            } else {
                System.out.println("*** New constellation: " + point);
                constellations.add(new Constellation(point));
            }
        });

        return constellations.size();
    }


    private class Constellation {
        Set<Point> points = Sets.newHashSet();

        public Constellation(Point point) {
            this.join(point);
        }

        public void join(Point point) {
            points.add(point);
        }

        public boolean isMember(Point point) {
            return points.stream().anyMatch(p -> p.distance(point) <= 3);
        }
    }

    private class Point {
        int x;
        int y;
        int z;
        int time;

        Point(String commaDelimitedValues) {
            String[] values = commaDelimitedValues.split(",");
            if (values.length != 4) {
                throw new IllegalArgumentException("Invalid format:" + commaDelimitedValues);
            }
            this.x = Integer.parseInt(values[0]);
            this.y = Integer.parseInt(values[1]);
            this.z = Integer.parseInt(values[2]);
            this.time = Integer.parseInt(values[3]);
        }


        public int distance(Point other) {
            int dist = Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z) + Math.abs(time - other.time);
            System.out.println(this + " " + other + " distance: " + dist);
            return dist;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", time=" + time +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y &&
                    z == point.z &&
                    time == point.time;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, y, z, time);
        }
    }
}
