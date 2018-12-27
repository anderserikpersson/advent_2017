package aoc2018_4.a2;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {


    public static void main(String[] args) throws IOException, URISyntaxException {
        Main main = new Main();
        System.out.println("Result=" + main.solve("aoc2018_4/input.txt"));
    }

    private Integer solve(String filePath) throws IOException, URISyntaxException {


        URI input = ClassLoader.getSystemResource(filePath).toURI();
        List<ReposeRecord> records = Files.lines(Paths.get(input)).map(ReposeRecord::new).sorted(Comparator.comparing(ReposeRecord::getTime)).collect(Collectors.toList());

        Hashtable<Integer, List<TimeInterval>> timeTable = parseRecords(records);

        int maxNrOfTimes = 0;
        int sleepyGuard = 0;
        int sleepyMinute = 0;

        for (Integer guardId : timeTable.keySet()) {
            List<TimeInterval> intervals = timeTable.get(guardId);
            CommonMinute commonMinute = findMostCommonMinute(intervals);
            if (commonMinute.nrOfTimes > maxNrOfTimes) {
                maxNrOfTimes = commonMinute.nrOfTimes;
                sleepyGuard = guardId;
                sleepyMinute = commonMinute.minute;
            }
        }

        return sleepyGuard * sleepyMinute;
    }

    private CommonMinute findMostCommonMinute(List<TimeInterval> intervals) {
        int[] minutes = new int[60];
        for (TimeInterval interval : intervals) {
            for (int minute = interval.start.getMinute(); minute < interval.stop.getMinute(); minute++) {
                minutes[minute]++;
            }
        }
        int index = IntStream.range(0, minutes.length).reduce((a, b) -> minutes[a] < minutes[b] ? b : a).orElseThrow(IllegalArgumentException::new);
        return new CommonMinute(index, minutes[index]);
    }

    private Hashtable<Integer, List<TimeInterval>> parseRecords(List<ReposeRecord> records) {
        Hashtable<Integer, List<TimeInterval>> guardSleeptimes = new Hashtable<>();
        LocalDateTime startSleep = null;
        Integer currentGuard = null;

        for (ReposeRecord record : records) {
            if (record.action == GuardAction.BEGIN) {
                currentGuard = record.guardId;
            } else if (record.action == GuardAction.SLEEP) {
                startSleep = record.getTime();
            } else if (record.action == GuardAction.WAKE_UP) {
                if (startSleep == null) {
                    throw new IllegalArgumentException("Starttime not set");
                } else if (currentGuard == null) {
                    throw new IllegalArgumentException("Guard not set");
                } else {
                    List<TimeInterval> sleepPeriod = guardSleeptimes.getOrDefault(currentGuard, Lists.newArrayList());
                    sleepPeriod.add(new TimeInterval(startSleep, record.getTime()));
                    guardSleeptimes.put(currentGuard, sleepPeriod);
                }
            }
        }
        return guardSleeptimes;
    }

    private enum GuardAction {
        BEGIN, SLEEP, WAKE_UP
    }

    private class CommonMinute {
        int minute;
        int nrOfTimes;

        public CommonMinute(int minute, int nrOfTimes) {
            this.minute = minute;
            this.nrOfTimes = nrOfTimes;
        }
    }

    private class TimeInterval {
        private LocalDateTime start;
        private LocalDateTime stop;

        TimeInterval(LocalDateTime start, LocalDateTime stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public String toString() {
            return "TimeInterval{" +
                    "start=" + start +
                    ", stop=" + stop +
                    '}';
        }
    }

    private class ReposeRecord {

        private LocalDateTime time;
        private Integer guardId;
        private GuardAction action;

        ReposeRecord(String line) {

            String timeStr = line.split("]")[0].substring(1);
            time = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            if (line.contains("#")) {
                guardId = Integer.parseInt((line.split("#")[1].split(" ")[0]));
            } else {
                guardId = null;
            }

            String actionStr = line.split(" ")[2];
            if (actionStr.startsWith("G")) {
                action = GuardAction.BEGIN;
            } else if (actionStr.startsWith("f")) {
                action = GuardAction.SLEEP;
            } else if (actionStr.startsWith("w")) {
                action = GuardAction.WAKE_UP;
            } else {
                throw new IllegalArgumentException("Invalid action");
            }
        }

        LocalDateTime getTime() {
            return time;
        }

        public Integer getGuardId() {
            return guardId;
        }

        public GuardAction getAction() {
            return action;
        }

        @Override
        public String toString() {
            return "ReposeRecord{" +
                    "time=" + time +
                    ", guardId=" + guardId +
                    ", action=" + action +
                    '}';
        }
    }
}
