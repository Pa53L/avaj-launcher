package aircrafts;

// import aircrafts.Aircraft;
import weather_tower.WeatherTower;

public class JetPlane extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    JetPlane(String name, Coordinates coordinates) {
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
                newLatitude += 10;
                newHeight = Math.min(newHeight + 2, 100);
                break;
            case ("SNOW"):
                System.out.println("SNOW");
                newHeight = Math.max(0, newHeight - 7);
                break;
            case ("RAIN"):
                System.out.println("RAIN");
                newLatitude =+ 5;
                break;
            case ("FOG"):
                System.out.println("FOG");
                newLatitude += 1;
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
        weatherTower.register(this);
    }
}
