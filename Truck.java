// Truck.java
package carrace.cars;

import carrace.track.WeatherType;
import java.util.HashMap;
import java.util.Map;

public class Truck extends Car {
    public Truck(String model) {
        super(model, 150.0, 1.2, 90, 50, getDefaultWeatherMap());
    }

    @Override
    public int getRefuelTime() {
        return 7;
    }

    @Override
    public double getAcceleration() {
        return (getFuelLevel() > 40) ? 7.0 : 5.0;
    }

    @Override
    public double getFuelFactor() {
        return 4;
    }

    private static Map<WeatherType, Double> getDefaultWeatherMap() {
        Map<WeatherType, Double> map = new HashMap<>();
        map.put(WeatherType.SUNNY, 1.0);
        map.put(WeatherType.RAINY, 0.9);
        map.put(WeatherType.SNOWY, 0.75);
        map.put(WeatherType.FOGGY, 0.8);
        return map;
    }
}


