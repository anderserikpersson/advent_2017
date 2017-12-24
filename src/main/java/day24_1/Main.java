package day24_1;


import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class Main {

    int maxStrength = 0;

    public static void main(String[] args) throws IOException {
        Main main = new Main();

        List<Component> components = Lists.newArrayList(
                new Component(25, 13),
                new Component(4, 43),
                new Component(42, 42),
                new Component(39, 40),
                new Component(17, 18),
                new Component(30, 7),
                new Component(12, 12),
                new Component(32, 28),
                new Component(9, 28),
                new Component(1, 1),
                new Component(16, 7),
                new Component(47, 43),
                new Component(34, 16),
                new Component(39, 36),
                new Component(6, 4),
                new Component(3, 2),
                new Component(10, 49),
                new Component(46, 50),
                new Component(18, 25),
                new Component(2, 23),
                new Component(3, 21),
                new Component(5, 24),
                new Component(46, 26),
                new Component(50, 19),
                new Component(26, 41),
                new Component(1, 50),
                new Component(47, 41),
                new Component(39, 50),
                new Component(12, 14),
                new Component(11, 19),
                new Component(28, 2),
                new Component(38, 47),
                new Component(5, 5),
                new Component(38, 34),
                new Component(39, 39),
                new Component(17, 34),
                new Component(42, 16),
                new Component(32, 23),
                new Component(13, 21),
                new Component(28, 6),
                new Component(6, 20),
                new Component(1, 30),
                new Component(44, 21),
                new Component(11, 28),
                new Component(14, 17),
                new Component(33, 33),
                new Component(17, 43),
                new Component(31, 13),
                new Component(11, 21),
                new Component(31, 39),
                new Component(0, 9),
                new Component(13, 50),
                new Component(10, 14),
                new Component(16, 10),
                new Component(3, 24),
                new Component(7, 0),
                new Component(50, 50));
        System.out.println("Result=" + main.solve(components, 0, 0));
    }

    private int solve(List<Component> components, int port, int totalStrength) {

        components.forEach(component -> {
            if (component.hasPort(port)) {
                int nextPort = component.getPort1() == port ? component.getPort2() : component.getPort1();
                List<Component> availableComponents = Lists.newArrayList(components);
                availableComponents.remove(component);
                int newTotal = totalStrength + component.getStrength();
                maxStrength = Math.max(newTotal, maxStrength);
                solve(availableComponents, nextPort, newTotal);
            }
        });
        return maxStrength;
    }


    private static class Component {
        private int port1;
        private int port2;

        private Component(int port1, int port2) {
            this.port1 = port1;
            this.port2 = port2;
        }

        public int getPort1() {
            return port1;
        }

        public void setPort1(int port1) {
            this.port1 = port1;
        }

        public int getPort2() {
            return port2;
        }

        public void setPort2(int port2) {
            this.port2 = port2;
        }

        public boolean hasPort(int port) {
            return port1 == port || port2 == port;
        }

        public int getStrength() {
            return port1 + port2;
        }

        @Override
        public String toString() {
            return port1 + "/" + port2;
        }
    }


}
