package avaj_launcher;

import java.io.BufferedReader;
import java.io.FileReader;

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
            return ;
        }
        try (FileReader reader = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(reader) ) {
            
            String line;
            int times = Integer.parseInt(br.readLine());
            WeatherTower weatherTower = new WeatherTower();
            System.out.println(times);
            while ((line  = br.readLine()) != null) {
                String[] aircraft = line.split(" ");
                
                if (aircraft.length == 5) {
                    String type = aircraft[0];
                    String name = aircraft[1];
                    int longitude = Integer.parseInt(aircraft[2]);
                    int latitude = Integer.parseInt(aircraft[3]);
                    int height = Integer.parseInt(aircraft[4]);
                    Flyable f = AircraftFactory.newAircraft(type, name, longitude, latitude, height);
                    f.registerTower(weatherTower);
                    // weatherTower.register(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

