package aircrafts;

import weather.WeatherTower;

import static utils.Logger.log;

public class Baloon extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    Baloon(String name, Coordinates coordinates) {
        super(name, coordinates);
        super.name = this.getClass().getSimpleName() + "#" + name + "(" + id + ")";
    }

    public void updateConditions() {
        String curWeather = weatherTower.getWeather(coordinates);
        int newLongitude = coordinates.getLongitude();
        int newLatitude = coordinates.getLatitude();
        int newHeight = coordinates.getHeight();

        switch (curWeather) {
            case ("SUN"):
                log(name + ": Let's enjoy the good weather and take some pics.");
                newLongitude += 2;
                newHeight = Math.min(newHeight + 4, 100);
                break;
            case ("SNOW"):
                log(name + ": It's snowing. We're gonna crash.");
                newHeight = Math.max(0, newHeight - 15);
                break;
            case ("RAIN"):
                log(name + ": Damn your rain! You messed up my baloon.");
                newHeight = Math.max(0, newHeight - 5);
                break;
            case ("FOG"):
                log(name + ": F**ng fog. We're like blind kittens.");
                newHeight = Math.max(0, newHeight - 3);
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