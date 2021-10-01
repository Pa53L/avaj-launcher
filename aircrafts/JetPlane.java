package aircrafts;

// import aircrafts.Aircraft;
import weather.WeatherTower;

import static utils.Logger.log;

public class JetPlane extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    JetPlane(String name, Coordinates coordinates) {
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
                log(name + ": I'm a firestarter, twisted firestarter");
                newLatitude += 10;
                newHeight = Math.min(newHeight + 2, 100);
                break;
            case ("SNOW"):
                log(name + ": OMG! Winter is coming!");
                newHeight = Math.max(0, newHeight - 7);
                break;
            case ("RAIN"):
                log(name + ": It's raining. Better watch out for lightnings.");
                newLatitude += 5;
                break;
            case ("FOG"):
                log(name + ": The radar won't let you down!");
                newLatitude += 1;
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
