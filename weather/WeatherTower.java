package weather;

import aircrafts.Coordinates;

public class WeatherTower extends Tower {
    public String getWeather(Coordinates coordinates) {
        return WeatherProvider.getProvider().getCurrentWeather(coordinates);
    }

    void changeWeather() {
        conditionsChanged();
    }

    public void simulatorStep() {
        changeWeather();
    }
}