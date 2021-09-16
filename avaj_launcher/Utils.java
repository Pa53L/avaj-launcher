package avaj_launcher;

import Exceptions.InvalidAircraftException;
import aircrafts.AircraftFactory;
import aircrafts.Flyable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {
    private static final String SIMULATION = "simulation.txt";
    static int simulationCounter = 0;
    static List<Flyable> flyables = new ArrayList<>();

    public static void scenarioParser(String path) {
        try (FileReader reader = new FileReader(path);
             BufferedReader br = new BufferedReader(reader)) {
            System.out.println(path);
            String line;
            try {
                simulationCounter = Integer.parseInt(br.readLine());
            } catch (Exception e) {
                System.err.println("First line must be an Integer");
            }
            while ((line = br.readLine()) != null) {
                int longitude = 0;
                int latitude = 0;
                int height = 0;
                String type;
                String name;
                String[] aircraft = line.split(" ");
                if (aircraft.length == 5) {
                    if (validateType(aircraft[0])) {
                        type = aircraft[0];
                    } else {
                        throw new IllegalArgumentException();
                    }
                    name = aircraft[1];
                    try {
                        longitude = Integer.parseInt(aircraft[2]);
                        latitude = Integer.parseInt(aircraft[3]);
                        height = Integer.parseInt(aircraft[4]);
                        if (latitude <= 0 || longitude <= 0 || height <= 0) {
                            throw new IllegalArgumentException();
                        }
                        if (height > 100) {
                            height = 100;
                        }
                    } catch (Exception e) {
                        throw new InvalidAircraftException();
                    }
                    flyables.add(AircraftFactory.newAircraft(type, name, longitude, latitude, height));
                } else {
                    throw new InvalidAircraftException();
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid file path or invalid file!");
        }
    }

    public static boolean validateType(String type) {
        return type.toLowerCase(Locale.ROOT).equals("baloon")
                || type.toLowerCase(Locale.ROOT).equals("helicopter")
                || type.toLowerCase(Locale.ROOT).equals("jetplane");
    }

    public static void writeFile(List<String> log) {
        try {
            Files.deleteIfExists(Paths.get(SIMULATION));
            BufferedWriter writer = new BufferedWriter(new FileWriter(SIMULATION));
            for (String s : log) {
                writer.write(s + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
