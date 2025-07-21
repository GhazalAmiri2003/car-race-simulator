// ElectricCar.java
package carrace.cars;

import carrace.track.WeatherType;
import java.util.HashMap;
import java.util.Map;

public class ElectricCar extends Car {
    public ElectricCar(String model) {
        super(model, 180.0, 0.9, 70, 100, getDefaultWeatherMap());
    }

    @Override
    public int getRefuelTime() {
        return 3;
    }
    //پلی مورفیزم    امضا
    @Override
    public double getAcceleration() {
        return (getFuelLevel() > 30) ? 9.0 : 7.5;
    }

    @Override
    public double getFuelFactor() {
        return 2.8;
    }

    private static Map<WeatherType, Double> getDefaultWeatherMap() {
        Map<WeatherType, Double> map = new HashMap<>();
        map.put(WeatherType.SUNNY, 1.0);
        map.put(WeatherType.RAINY, 0.85);
        map.put(WeatherType.SNOWY, 0.65);
        map.put(WeatherType.FOGGY, 0.75);
        return map;
    }
}


