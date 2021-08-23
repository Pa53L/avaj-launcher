package aircrafts;

import weather_tower.WeatherTower;

public class Baloon extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    Baloon(String name, Coordinates coordinates) {
        super(name, coordinates);
        System.out.println(name + " " + super.coordinates.getLongitude() + " " + super.coordinates.getLatitude() + " " + super.coordinates.getHeight());
    }

    public void updateConditions() {
        String curWeather = weatherTower.getWeather(coordinates);
        int newLongitude = coordinates.getLongitude();
        int newLatitude = coordinates.getLatitude();
        int newHeight = coordinates.getHeight();

        switch (curWeather) {
            case ("SUN"):
                System.out.println("SUN");
                newLongitude += 2;
                newHeight = Math.min(newHeight + 4, 100);
                break;
            case ("SNOW"):
                System.out.println("SNOW");
                newHeight = Math.max(0, newHeight - 15);
                break;
            case ("RAIN"):
                System.out.println("RAIN");
                newHeight = Math.max(0, newHeight - 5);
                break;
            case ("FOG"):
                System.out.println("FOG");
                newHeight = Math.max(0, newHeight - 3);
                break;
        }
        coordinates = new Coordinates(newLongitude, newLatitude, newHeight);
        if (coordinates.getHeight() == 0) {
            System.out.println(this.getClass().getSimpleName() + "#" + name + "("+id+")" + " unregistered from weather tower");
            weatherTower.unregister(this);
        }
    }

    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
        weatherTower.register(this);
    }

}