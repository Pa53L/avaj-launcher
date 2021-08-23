package aircrafts;
import weather_tower.WeatherTower;

public class Helicopter extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    Helicopter(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    public void updateConditions() {
        String curWeather = weatherTower.getWeather(super.coordinates);
        int newLongitude = coordinates.getLongitude();
        int newLatitude = coordinates.getLatitude();
        int newHeight = coordinates.getHeight();

        switch (curWeather) {
            case ("SUN"):
                System.out.println("SUN");
                newLongitude += 10;
                newHeight = Math.min(newHeight + 2, 100);
                break;
            case ("SNOW"):
                System.out.println("SNOW");
                newHeight = Math.max(0, newHeight - 12);
                break;
            case ("RAIN"):
                System.out.println("RAIN");
                newLongitude =+ 5;
                break;
            case ("FOG"):
                System.out.println("FOG");
                newLongitude += 1;
                break;
        }
        coordinates = new Coordinates(newLongitude, newLatitude, newHeight);
        if (coordinates.getHeight() == 0) {
            System.out.println(this.getClass().getName() + "#" + name + "("+id+")" + " unregistered from weather tower");
            weatherTower.unregister(this);
        }
    }

    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
    }
}