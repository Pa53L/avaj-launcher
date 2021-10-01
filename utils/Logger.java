package utils;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static final List<String> simulationLog = new ArrayList<>();
    public static void log(String s) {
        simulationLog.add(s);
    }

    public static List<String> getSimulationLog() {
        return simulationLog;
    }
}
