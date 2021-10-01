package weather;

import aircrafts.Coordinates;

public class WeatherProvider {
    private static WeatherProvider weatherProvider;
    private static String[] weather;
    
    private WeatherProvider() {
        weather = new String[]{"SUN", "SNOW", "RAIN", "FOG"};
    }

    public static WeatherProvider getProvider() {
        if (weatherProvider == null) {
            weatherProvider = new WeatherProvider();
        }
        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        int currentWeather = (coordinates.getLongitude() + coordinates.getLatitude() + coordinates.getHeight()) % 4;
        return weather[currentWeather];
    }
}
