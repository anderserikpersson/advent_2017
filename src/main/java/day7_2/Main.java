package day7_2;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        main.solve();
    }

    private void solve() throws IOException, URISyntaxException {
        Main main = new Main();
        URI input = ClassLoader.getSystemResource("day7/input.txt").toURI();
        Map<String, NodeDescription> nodeDescriptions =
                Maps.uniqueIndex(Files.lines(Paths.get(input))
                        .map(main::mapToNodeDescription)
                        .collect(Collectors.toList()), NodeDescription::getName);
        Node tree = main.buildTree(findRoot(nodeDescriptions), nodeDescriptions, 0);
        check(tree);
    }

    private void check(Node parent) {
        if (!childsInBalance(parent.getChilds())) {
            Node balanced = getBalancedChild(parent);
            Node unbalanced = getUnbalancedChild(parent);

            if (childsInBalance(unbalanced.getChilds())) {
                System.out.println(unbalanced.getWeight() + balanced.getTotalWeigt() - unbalanced.getTotalWeigt());
            } else {
                unbalanced.getChilds().forEach(this::check);
            }
        }
    }

    private Node getBalancedChild(Node parent) {
        Map<Integer, List<Node>> groupedChildWeights = parent.getChilds().stream().collect(Collectors.groupingBy(Node::getTotalWeigt));
        return groupedChildWeights
                .values()
                .stream()
                .filter(nodes -> nodes.size() != 1)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find a balanced node"))
                .get(0);
    }

    private Node getUnbalancedChild(Node parent) {
        Map<Integer, List<Node>> groupedChildWeights = parent.getChilds().stream().collect(Collectors.groupingBy(Node::getTotalWeigt));
        return groupedChildWeights
                .values()
                .stream()
                .filter(nodes -> nodes.size() == 1)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find an unbalanced node"))
                .get(0);
    }

    private boolean childsInBalance(List<Node> childs) {
        return childs.isEmpty() || childs.stream().allMatch(node -> node.getTotalWeigt().equals(childs.get(0).getTotalWeigt()));
    }

    private NodeDescription mapToNodeDescription(String line) {
        String[] descriptions = line.split("->");
        String name = descriptions[0].split(" ")[0];
        String weightInstruction = descriptions[0].split(" ")[1];
        Integer weight = Integer.parseInt(weightInstruction.substring(1, weightInstruction.length() - 1));

        if (descriptions.length != 2) {
            return new NodeDescription(name, weight, Collections.emptyList());
        } else {
            List<String> childs = Lists.newArrayList(descriptions[1].trim().split(", "));
            return new NodeDescription(name, weight, childs);
        }
    }

    private String findRoot(Map<String, NodeDescription> nodeDescriptions) {
        Set<String> possibleRoots = Sets.newHashSet();
        Set<String> childs = Sets.newHashSet();
        if (nodeDescriptions.size() == 1) {
            return nodeDescriptions.values().iterator().next().getName();
        }
        nodeDescriptions.values().forEach(nodeDescription -> {
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

    private Node buildTree(String parent, Map<String, NodeDescription> nodeDescriptions, Integer currentWeight) {
        NodeDescription parentNode = nodeDescriptions.get(parent);
        if (parentNode.getChilds().isEmpty()) {
            return new Node(parent, parentNode.getWeight(), parentNode.getWeight(), Collections.emptyList());
        } else {
            List<Node> childs = parentNode.getChilds().stream().map(s -> buildTree(s, nodeDescriptions, 0)).collect(Collectors.toList());
            Integer childsTotalWeight = childs.stream().collect(Collectors.summingInt(Node::getTotalWeigt));
            return new Node(parent, parentNode.getWeight(), parentNode.getWeight() + childsTotalWeight, childs);
        }
    }

    private class Node {
        private String name;
        private Integer weight;
        private Integer totalWeigt;
        private List<Node> childs;

        Node(String name, Integer weight, Integer totalWeigt, List<Node> childs) {
            this.name = name;
            this.weight = weight;
            this.totalWeigt = totalWeigt;
            this.childs = childs;
        }

        String getName() {
            return name;
        }

        Integer getWeight() {
            return weight;
        }

        Integer getTotalWeigt() {
            return totalWeigt;
        }

        List<Node> getChilds() {
            return childs;
        }
    }

    private class NodeDescription {
        private String name;
        private Integer weight;
        private List<String> childs;

        NodeDescription(String name, Integer weight, List<String> childs) {
            this.name = name;
            this.weight = weight;
            this.childs = childs;
        }

        String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        Integer getWeight() {
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