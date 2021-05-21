package aircrafts;

import weather_tower.WeatherTower;

public class Baloon extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    Baloon(String name, Coordinates coordinates) {
        super(name, coordinates);
        System.out.println(name + " " + super.coordinates.getLongitude() + " " + super.coordinates.getLatitude() + " " + super.coordinates.getHeight());
    }

    public void updateConditions() {

    }

    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
    }

}