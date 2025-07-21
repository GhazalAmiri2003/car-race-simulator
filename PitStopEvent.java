// ✅ PitStopEvent.java
package carrace.core;

import carrace.cars.Car;
import carrace.driver.Driver;
import carrace.fuel.FuelSystem;
import carrace.track.EnvironmentCondition;
import carrace.track.Track;

public class PitStopEvent extends RaceEvent {
    private final Car car;
    private final Driver driver;
    private final double pitStopPosition;
    private final FuelSystem fuelSystem = new FuelSystem();

    public PitStopEvent(double eventTime, Car car, Driver driver, double pitStopPosition) {
        super(eventTime);
        this.car = car;
        this.driver = driver;
        this.pitStopPosition = pitStopPosition;
    }

    @Override
    public void execute(RaceSimulator simulator) {
        if (simulator.isEliminated(car)) return;

        Track track = simulator.getTrack();
        EnvironmentCondition weather = simulator.getEnvironmentCondition();
        double distanceToFinish =
                Math.max(0.0, track.getFinishLine() - car.getCurrentPosition());

        // <-- اینجا: مستقیم با پنج آرگومان فراخوانی کن
       boolean doPit =  driver
                .getPitStrategy()
                .shouldPit(
                        car,
                        track,
                        weather,
                        driver,            // <— همین‌جا شیء Driver خودت را بده
                        distanceToFinish
                );

        double pos = car.getCurrentPosition();
        if (doPit) {
            System.out.printf("[%.1fs] %s    ✅ENTERING PIT at %.2f km (fuel=%.2f)%n",
                    eventTime, car.getModel(), pos, car.getFuelLevel());
            fuelSystem.refuel(car);
            simulator.addEvent(new AccelerateEvent(
                    eventTime + car.getRefuelTime(), car, driver));
        } else {
            System.out.printf("[%.1fs] %s SKIPPED pit ❌ at %.2f km (fuel=%.2f)%n",
                    eventTime, car.getModel(), pos, car.getFuelLevel());
            simulator.addEvent(new AccelerateEvent(
                    eventTime + simulator.getTimeStep(), car, driver));
        }
    }
}
