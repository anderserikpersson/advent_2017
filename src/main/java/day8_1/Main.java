package day8_1;


import com.google.common.collect.Maps;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        URI input = ClassLoader.getSystemResource("day8/input.txt").toURI();
        List<Instruction> instructions = Files.lines(Paths.get(input)).map(main::mapToInstruction).collect(Collectors.toList());
        Map<String, Integer> registers = main.compute(instructions);
        System.out.println("Result:" + registers.entrySet().stream().max(Map.Entry.comparingByValue()));
    }

    private Map<String, Integer> compute(List<Instruction> instructions) {
        Map<String, Integer> registers = Maps.newHashMap();
        instructions.forEach(instruction -> {
            int registerValue = registers.getOrDefault(instruction.register, 0);
            if (instruction.condition.evaluate(registers.getOrDefault(instruction.condition.register, 0))) {
                registerValue = instruction.calculate(registerValue);
                registers.put(instruction.register, registerValue);
            }
        });
        return registers;
    }

    private Instruction mapToInstruction(String line) {
        String[] tokens = line.split(" ");
        if (tokens.length != 7) {
            throw new IllegalArgumentException("Invalid instruction");
        } else {
            Condition condition = new Condition(tokens[4], Comparator.fromToken(tokens[5]), Integer.parseInt(tokens[6]));
            return new Instruction(tokens[0], Operator.fromToken(tokens[1]), Integer.parseInt(tokens[2]), condition);
        }
    }

    private enum Operator {
        INC, DEC;

        public static Operator fromToken(String token) {
            switch (token.toUpperCase()) {
                case "INC":
                    return INC;
                case "DEC":
                    return DEC;
                default:
                    throw new IllegalArgumentException("Invalid token");
            }
        }
    }

    private enum Comparator {
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL,
        LESSER_THAN,
        LESSER_THAN_OR_EQUAL,
        EQUAL_TO,
        NOT_EQUAL_TO;

        public static Comparator fromToken(String token) {
            switch (token) {
                case "==":
                    return EQUAL_TO;
                case ">":
                    return GREATER_THAN;
                case ">=":
                    return GREATER_THAN_OR_EQUAL;
                case "<":
                    return LESSER_THAN;
                case "<=":
                    return LESSER_THAN_OR_EQUAL;
                case "!=":
                    return NOT_EQUAL_TO;
                default:
                    throw new IllegalArgumentException("Invalid token");
            }
        }
    }

    private class Instruction {
        private String register;
        private Operator operator;
        private int value;
        private Condition condition;

        Instruction(String register, Operator operator, int value, Condition condition) {
            this.register = register;
            this.operator = operator;
            this.value = value;
            this.condition = condition;
        }

        int calculate(int registerValue) {
            switch (operator) {
                case INC:
                    return registerValue + value;
                case DEC:
                    return registerValue - value;
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "register='" + register + '\'' +
                    ", operator=" + operator +
                    ", value=" + value +
                    ", condition=" + condition +
                    '}';
        }
    }

    private class Condition {
        private String register;
        private Comparator comparator;
        private int value;

        Condition(String register, Comparator comparator, int value) {
            this.register = register;
            this.comparator = comparator;
            this.value = value;
        }

        boolean evaluate(int registerValue) {
            switch (comparator) {
                case EQUAL_TO:
                    return registerValue == value;
                case NOT_EQUAL_TO:
                    return registerValue != value;
                case GREATER_THAN:
                    return registerValue > value;
                case GREATER_THAN_OR_EQUAL:
                    return registerValue >= value;
                case LESSER_THAN:
                    return registerValue < value;
                case LESSER_THAN_OR_EQUAL:
                    return registerValue <= value;
                default:
                    throw new IllegalArgumentException("Invalid comparator");
            }
        }

        @Override
        public String toString() {
            return "Condition{" +
                    "register='" + register + '\'' +
                    ", comparator=" + comparator +
                    ", value=" + value +
                    '}';
        }
    }
}