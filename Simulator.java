import java.util.List;

import aircrafts.Flyable;
import utils.Logger;
import utils.Parser;
import weather.WeatherTower;

public class Simulator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("You should input only one argument. It must be text file.");
            return ;
        }
        try {
            Parser.scenarioParser(args[0]);
            startSimulation(Parser.simulationCounter, Parser.flyables);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        List<String> log = Logger.getSimulationLog();
        for (String s : log) {
            System.out.println(s);
        }
        Parser.writeFile(log);
    }

    private static void startSimulation(int counter, List<Flyable> flyables) {
        WeatherTower weatherTower = new WeatherTower();
        for (Flyable f : flyables) {
            f.registerTower(weatherTower);
        }
        for (int i = 0; i < counter; i++) {
            weatherTower.simulatorStep();
        }
    }
}

