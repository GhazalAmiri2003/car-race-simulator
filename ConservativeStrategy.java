// ✅ ConservativeStrategy.java
package carrace.driver;

import carrace.cars.Car;
import carrace.track.EnvironmentCondition;
import carrace.track.Track;
//strategy pattern
public class ConservativeStrategy implements RaceStrategy {
    @Override
    public double decideAcceleration(Car car, Track track, EnvironmentCondition weather, Driver driver) {
        double base = car.getAcceleration();
        double skillFactor = driver.getSkillLevel() / 10.0;
        double error = driver.getErrorFactor();
        return base * (1.0 + skillFactor) * (1.0 - error * 0.5);
    }

    @Override
    public double decideBrakeFactor(Car car, Track track, EnvironmentCondition weather, Driver driver, double distanceToCurve) {
        double sharpness = track.getCurveSharpness();
        double risk = sharpness * weather.getWeatherRisk();
        double skillImpact = 1.0 - (driver.getSkillLevel() / 10.0);
        double error = driver.getErrorFactor();
        return risk * (1.0 + skillImpact + error);
    }

    @Override
    public double getErrorMultiplier() {
        return 1.0;
    }
}