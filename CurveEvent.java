// ✅ CurveEvent.java
package carrace.core;

import carrace.cars.Car;
import carrace.driver.Driver;
import carrace.track.EnvironmentCondition;
import carrace.track.Track;

public class CurveEvent extends RaceEvent {
    private final Car car;
    private final Driver driver;
    private final double curvePosition;

    public CurveEvent(double eventTime, Car car, Driver driver, double curvePosition) {
        super(eventTime);
        this.car = car;
        this.driver = driver;
        this.curvePosition = curvePosition;
    }

    @Override
    public void execute(RaceSimulator simulator) {
        if (simulator.isEliminated(car)) return;

        Track track = simulator.getTrack();
        EnvironmentCondition weather = simulator.getEnvironmentCondition();

        System.out.printf("[%.1fs] %s entered a curve at %.2f km ❗〽️%n",
                eventTime, car.getModel(), curvePosition);

        double brakeFactor = driver.decideBrakeFactor(car, track, weather, 0);
        double deceleration = car.getCurrentSpeed() * brakeFactor;
        double newSpeed = Math.max(0.0, car.getCurrentSpeed() - deceleration);
        car.setCurrentSpeed(newSpeed);

        double stability = car.getAdjustedStability(weather);
        double risk = (newSpeed / car.getMaxSpeed()) * (1.2 / stability)
                * weather.getWeatherRisk() * (1.2 - driver.getSkillLevel() / 10.0)
                * (1 + driver.getErrorFactor());

        if (Math.random() < risk) {
            simulator.addEvent(new CrashEvent(eventTime + simulator.getTimeStep(), car, driver));
        }

        simulator.addEvent(new AccelerateEvent(eventTime + simulator.getTimeStep(), car, driver));
    }
}

