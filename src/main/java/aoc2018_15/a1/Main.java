package aoc2018_15.a1;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) throws URISyntaxException, IOException {
        Main main = new Main();
        main.solve("aoc2018_15/input.txt");
    }

    private void solve(String filePath) throws URISyntaxException, IOException {
        List<String> lines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(filePath));
        Dungeon dungeon = new Dungeon(lines);
        dungeon.print();

        List<Elf> elfs = dungeon.findContent(Content.ELF).stream().map(Elf::new).collect(Collectors.toList());
        List<Goblin> goblins = dungeon.findContent(Content.GOBLIN).stream().map(Goblin::new).collect(Collectors.toList());

        dungeon.executeMoves();
    }


    private enum Content {
        WALL('#'),
        OPEN('.'),
        ELF('E'),
        GOBLIN('G'),
        INRANGE('?'),
        REACHABLE('@'),
        NEAREST('!'),
        CHOSEN('+');


        Character symbol;

        Content(Character symbol) {
            this.symbol = symbol;
        }

        public static Content fromSymbol(Character symbol) {
            for (Content content : Content.values()) {
                if (content.getSymbol().equals(symbol)) {
                    return content;
                }
            }
            throw new IllegalArgumentException("Unknown symbol:" + symbol);
        }

        public Character getSymbol() {
            return symbol;
        }
    }

    private class Dungeon {
        Hashtable<Position, Content> cells = new Hashtable<>(1000);

        int sizeX = 0;
        int sizeY = 0;

        public Dungeon(List<String> cellRows) {
            if (cellRows == null || cellRows.size() == 0) {
                throw new IllegalArgumentException("Dungeon need to have a size");
            }
            sizeX = cellRows.get(0).length();
            sizeY = cellRows.size();

            for (int y = 0; y < sizeY; y++) {
                String cellRow = cellRows.get(y);
                for (int x = 0; x < sizeX; x++) {
                    Content content = Content.fromSymbol(cellRow.charAt(x));
                    cells.put(new Position(x, y), content);
                }
            }
        }

        public void print() {
            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    Content content = getContent(x, y);
                    System.out.print(content.getSymbol());
                }
                System.out.println();
            }
        }


        public Content getContent(Position pos) {
            return cells.get(pos);
        }

        public Content getContent(int x, int y) {
            return getContent(new Position(x, y));
        }

        public List<Position> findContent(Content content) {
            return cells.keySet().stream().filter(position -> cells.get(position) == content).collect(Collectors.toList());
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public void executeMoves() {


            for (int y = 0; y < getSizeY(); y++) {
                for (int x = 0; x < getSizeX(); x++) {
                    Content content = getContent(x, y);
                    if (content == Content.ELF) {
                        moveElf(new Position(x, y));
                    }
                }
            }
        }

        private void moveElf(Position position) {

            List<Goblin> goblins = findContent(Content.GOBLIN).stream().map(Goblin::new).collect(Collectors.toList());
            Set<Position> inRange = goblins.stream().map(g -> getInRange(g.position)).flatMap(Set::stream).collect(Collectors.toSet());


            inRange.forEach(pos -> cells.put(pos, Content.INRANGE));
            print();
        }

        private Set<Position> getInRange(Position pos) {
            Set<Position> result = Sets.newHashSet();
            result.add(new Position(pos.x - 1, pos.y));
            result.add(new Position(pos.x + 1, pos.y));
            result.add(new Position(pos.x, pos.y + 1));
            result.add(new Position(pos.x, pos.y - 1));

            return result.stream().filter(position -> getContent(position) == Content.OPEN).collect(Collectors.toSet());
        }


    }

    private class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private abstract class Creature {
        int hits = 200;
        int ap = 3;
        Position position;

        private Creature(Position position) {
            this.position = position;
        }

        abstract String typeName();

        @Override
        public String toString() {
            return typeName() + "{" +
                    "hits=" + hits +
                    ", ap=" + ap +
                    ", position=" + position +
                    '}';
        }
    }

    private class Elf extends Creature {
        public Elf(Position position) {
            super(position);
        }

        @Override
        String typeName() {
            return "Elf";
        }

    }

    private class Goblin extends Creature {

        public Goblin(Position position) {
            super(position);
        }

        @Override
        String typeName() {
            return "Goblin";
        }
    }

}
