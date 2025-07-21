// FormulaCar.java
package carrace.cars;

import carrace.track.WeatherType;
import java.util.HashMap;
import java.util.Map;

public class FormulaCar extends Car {
    public FormulaCar(String model) {
        super(model, 220.0, 0.7, 50, 250, getDefaultWeatherMap());
    }

    @Override
    public int getRefuelTime() {
        return 6;
    }

    @Override
    public double getAcceleration() {
        return (getFuelLevel() > 25) ? 11.0 : 8.5;
    }

    @Override
    public double getFuelFactor() {
        return 3.5;
    }

    private static Map<WeatherType, Double> getDefaultWeatherMap() {
        Map<WeatherType, Double> map = new HashMap<>();
        map.put(WeatherType.SUNNY, 1.0);
        map.put(WeatherType.RAINY, 0.8);
        map.put(WeatherType.SNOWY, 0.6);
        map.put(WeatherType.FOGGY, 0.7);
        return map;
    }
}
