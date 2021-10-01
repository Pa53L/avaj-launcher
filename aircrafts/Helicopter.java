package aircrafts;
import weather.WeatherTower;

import static utils.Logger.log;

public class Helicopter extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    Helicopter(String name, Coordinates coordinates) {
        super(name, coordinates);
        super.name = this.getClass().getSimpleName() + "#" + name + "(" + id + ")";
    }

    public void updateConditions() {
        String curWeather = weatherTower.getWeather(super.coordinates);
        int newLongitude = coordinates.getLongitude();
        int newLatitude = coordinates.getLatitude();
        int newHeight = coordinates.getHeight();

        switch (curWeather) {
            case ("SUN"):
                log(name + ": This is hot.");
                newLongitude += 10;
                newHeight = Math.min(newHeight + 2, 100);
                break;
            case ("SNOW"):
                log(name + ": My rotor is going to freeze!");
                newHeight = Math.max(0, newHeight - 12);
                break;
            case ("RAIN"):
                log(name + ": I'm so wet, if you know what I mean!");
                newLongitude += 5;
                break;
            case ("FOG"):
                log(name + ": Fog of war is hiding our enemy's!");
                newLongitude += 1;
                break;
        }
        coordinates = new Coordinates(newLongitude, newLatitude, newHeight);
        if (coordinates.getHeight() == 0) {
            log(name + " landing");
            weatherTower.unregister(this);
        }
    }

    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
        weatherTower.register(this);
    }

    @Override
    public String getName() {
        return super.name;
    }
}