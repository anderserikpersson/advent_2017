package day24_1;


import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private int maxStrength = 0;

    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        URI input = ClassLoader.getSystemResource("day24/input.txt").toURI();
        List<Component> components = Files.lines(Paths.get(input)).map(Main::mapToComponent).collect(Collectors.toList());
        System.out.println("Result=" + main.solve(components, 0, 0));
    }

    private static Component mapToComponent(String input) {
        String[] ports = input.split("/");
        if (ports.length != 2) {
            throw new IllegalArgumentException("Invalid input: " + input);
        } else {
            return new Component(Integer.parseInt(ports[0]), Integer.parseInt(ports[1]));
        }
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

        int getPort1() {
            return port1;
        }

        void setPort1(int port1) {
            this.port1 = port1;
        }

        int getPort2() {
            return port2;
        }

        void setPort2(int port2) {
            this.port2 = port2;
        }

        boolean hasPort(int port) {
            return port1 == port || port2 == port;
        }

        int getStrength() {
            return port1 + port2;
        }

        @Override
        public String toString() {
            return port1 + "/" + port2;
        }
    }


}
