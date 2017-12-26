package day6_1;


import com.google.common.collect.Sets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        int[] banks = {14, 0, 15, 12, 11, 11, 3, 5, 1, 6, 8, 4, 9, 1, 8, 4};
        System.out.println("Result=" + main.solve(banks));
    }

    private long solve(int[] banks) {
        Set<String> oldBanks = Sets.newHashSet();
        int cycles = 0;

        while (!oldBanks.contains(banksToString(banks))) {
            cycles++;
            oldBanks.add(banksToString(banks));
            redistribute(banks);
        }
        return cycles;
    }

    private String banksToString(int[] banks) {
        StringBuilder builder = new StringBuilder(banks.length);
        Arrays.stream(banks).forEach(builder::append);
        return builder.toString();
    }

    private void redistribute(int[] banks) {
        int redisValue = 0;
        int redisIndex = 0;
        for (int index = 0; index < banks.length; index++) {
            if (banks[index] > redisValue) {
                redisValue = banks[index];
                redisIndex = index;
            }
        }
        banks[redisIndex] = 0;
        while (redisValue > 0) {
            redisIndex = (redisIndex + 1) % banks.length;
            banks[redisIndex]++;
            redisValue--;
        }
    }
}
