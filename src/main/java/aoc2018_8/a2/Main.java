package aoc2018_8.a2;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

public class Main {

    private String[] values = new String[100];
    private int valuePos = 0;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        System.out.println("Result=" + main.solve("aoc2018_8/input.txt"));
    }

    private static int sumMetadata(Node node) {
        int nrOfChilds = node.getChilds().size();
        if (nrOfChilds == 0) {
            return node.getMetadata().stream().mapToInt(Integer::intValue).sum();
        } else {
            int result = 0;

            for (int metadata : node.getMetadata()) {
                if (metadata <= nrOfChilds) {
                    Node child = node.getChilds().get(metadata - 1);
                    int childSum = sumMetadata(child);
                    result = result + childSum;
                }
            }
            return result;
        }
    }

    private long solve(String filePath) throws IOException {
        List<Node> nodes = Lists.newArrayList();

        String line = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(filePath));
        values = line.split(" ");

        Node node = readNode();
        return sumMetadata(node);
    }

    private Node readNode() {
        Header header = new Header(Integer.parseInt(values[valuePos++]), Integer.parseInt(values[valuePos++]));
        Node node = new Node(header);

        int currentChild = 0;
        while (currentChild < header.getNrOfChilds()) {
            node.addChild(readNode());
            currentChild++;
        }

        int currentMetadata = 0;
        while (currentMetadata < header.getNrOfMetadata()) {
            node.addMetadata(Integer.parseInt(values[valuePos++]));
            currentMetadata++;
        }

        return node;
    }

    private class Node {

        Header header;
        List<Node> childs = Lists.newArrayList();
        List<Integer> metadata = Lists.newArrayList();

        public Node(Header header) {
            this.header = header;
        }

        public List<Node> getChilds() {
            return childs;
        }

        public void setChilds(List<Node> childs) {
            this.childs = childs;
        }

        public void addChild(Node child) {
            this.childs.add(child);
        }

        public List<Integer> getMetadata() {
            return metadata;
        }

        public void setMetadata(List<Integer> metadata) {
            this.metadata = metadata;
        }

        public void addMetadata(Integer metadata) {
            this.metadata.add(metadata);
        }
    }

    private class Header {
        int nrOfChilds;
        int nrOfMetadata;

        public Header(int nrOfChilds, int nrOfMetadata) {
            this.nrOfChilds = nrOfChilds;
            this.nrOfMetadata = nrOfMetadata;
        }

        public int getNrOfChilds() {
            return nrOfChilds;
        }

        public void setNrOfChilds(int nrOfChilds) {
            this.nrOfChilds = nrOfChilds;
        }

        public int getNrOfMetadata() {
            return nrOfMetadata;
        }

        public void setNrOfMetadata(int nrOfMetadata) {
            this.nrOfMetadata = nrOfMetadata;
        }
    }


}
