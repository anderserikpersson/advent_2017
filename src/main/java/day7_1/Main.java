package day7_1;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        URI input = ClassLoader.getSystemResource("day7/input.txt").toURI();
        List<NodeDescription> nodeDescriptions = Files.lines(Paths.get(input)).map(Main::mapToNodeDescription).collect(Collectors.toList());
        System.out.println("Result=" + main.findRoot(nodeDescriptions));
    }

    private static NodeDescription mapToNodeDescription(String input) {
        String[] descriptions = input.split("->");
        String name = descriptions[0].split(" ")[0];
        String weightInstruction = descriptions[0].split(" ")[1];
        Integer weight = Integer.parseInt(weightInstruction.substring(1, weightInstruction.length() - 1));

        if (descriptions.length != 2) {
            return new NodeDescription(name, weight, null);
        } else {
            List<String> childs = Lists.newArrayList(descriptions[1].trim().split(", "));
            return new NodeDescription(name, weight, childs);
        }
    }

    private String findRoot(List<NodeDescription> nodeDescriptions) {
        Set<String> possibleRoots = Sets.newHashSet();
        Set<String> childs = Sets.newHashSet();
        if (nodeDescriptions.size() == 1) {
            return nodeDescriptions.get(0).getName();
        }
        nodeDescriptions.forEach(nodeDescription -> {
            if (nodeDescription.getChilds() != null) {
                childs.addAll(nodeDescription.getChilds());
                possibleRoots.add(nodeDescription.getName());
            }
        });

        possibleRoots.removeAll(childs);
        if (possibleRoots.size() != 1) {
            throw new IllegalArgumentException("Illegal number of roots: " + possibleRoots.size());
        }
        return possibleRoots.iterator().next();
    }


    private static class NodeDescription {
        private String name;
        private Integer weight;
        private List<String> childs;

        NodeDescription(String name, Integer weight, List<String> childs) {
            this.name = name;
            this.weight = weight;
            this.childs = childs;
        }

        @Override
        public String toString() {
            return "NodeDescription{" +
                    "name='" + name + '\'' +
                    ", weight=" + weight +
                    ", childs=" + childs +
                    '}';
        }

        String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        List<String> getChilds() {
            return childs;
        }

        public void setChilds(List<String> childs) {
            this.childs = childs;
        }
    }
}
