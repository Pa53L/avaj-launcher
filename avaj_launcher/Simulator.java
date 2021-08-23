package avaj_launcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import aircrafts.AircraftFactory;
import aircrafts.Flyable;
import weather_tower.WeatherTower;

public class Simulator {
    /*
        1. Координаты это положительные числа
        2. Высота может быть в диапазоне 0 - 100
        3. Каждый раз, когда создается aircraft он получает уникальный id
        4. Если aircraft достигает высоты 0 или должен спуститься ниже, он приземляется, снимается с метеорологической вышки и регистрирует свои координаты
        5. Когда происходит изменение погоды, каждый тип aircraft должен регистрировать сообщение следующего формата: TYPE # NAME (UNIQUE_ID): SPECIFIC_MESSAGE
        6. Каждый раз, когда aircraft регистрируется или снимается с регистрации погодной вышки, должно быть отражено сообщением
        7. Файл должен быть валидирован. Если файл некорректный программа останавливает выполнение. Все сообщения об ошибках выводятся в стандартный аутпут
        8. Бонусы: кастомные ексепшены, чтение контента, зашифрованного алгоритмом md5

        Формат входного файла:
        Первая строка - число (положительное), которое показывает сколько раз сменится погода
        Остальные строки - летательные аппараты, описанные следующим форматом:
        TYPE NAME LONGITUDE LATITUDE HEIGHT

        Погода:
        Существует 4 типа погоды:
        RAIN
        FOG
        SUN
        SNOW
        Каждая точка в пространстве имеет свои погодные условия
        Алгоритм генерации погоды по желанию, но должен учитывать координаты (ШРНА+ДОЛГОТА * ВЫСОТА % 3 -> выбираем из ENUM с типами погоды)

        Aircrafts
        JetPlane
            SUN - LATITUDE += 10; HEIGHT += 2;
            RAIN - LATITUDE += 5;
            FOG - LATITUDE +=1;
            SNOW - HEIGHT -= 7;
        Helicopter
            SUN - LONGITUDE += 10; HEIGHT += 2;
            RAIN - LONGITUDE += 5;
            FOG - LONGITUDE +=1;
            SNOW - HEIGHT -= 12;
        Baloon
            SUN - LONGITUDE += 2; HEIGHT += 4;
            RAIN - HEIGHT -= 5;
            FOG - HEIGHT -=3;
            SNOW - HEIGHT -= 15;
    */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("You should input only one argument. It must be text file.");
            return ;
        }
        try {
            Utils.scenarioParser(args[0]);
            startSimulation(Utils.simulationCounter, Utils.flyables);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void startSimulation(int counter, List<Flyable> flyables) {
        WeatherTower weatherTower = new WeatherTower();
        for (Flyable f : flyables) {
            f.registerTower(weatherTower);
        }
        for (int i = 0; i < counter; i++) {
            weatherTower.changeWeather();
        }
    }
}

class Utils {
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
            while ((line  = br.readLine()) != null) {
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
                        System.err.println("Invalid parameters of Aircraft");
                    }
                    flyables.add(AircraftFactory.newAircraft(type, name, longitude, latitude, height));
                } else {
                    throw new IllegalArgumentException();
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
}

