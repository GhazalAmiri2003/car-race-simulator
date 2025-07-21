// ✅ AccelerateEvent.java
package carrace.core;

import carrace.cars.Car;
import carrace.driver.Driver;
import carrace.track.EnvironmentCondition;
import carrace.track.Track;

public class AccelerateEvent extends RaceEvent {
    private final Car car;
    private final Driver driver;

    public AccelerateEvent(double eventTime, Car car, Driver driver) {
        super(eventTime);
        this.car = car;
        this.driver = driver;
    }

    @Override
    public void execute(RaceSimulator simulator) {
        if (simulator.isEliminated(car)) return;

        if (car.getFuelLevel() <= 0.0) {
            System.out.printf("[%.1fs] %s ❌🔋 OUT OF FUEL! Eliminated.%n", eventTime, car.getModel());
            simulator.eliminateCar(car);
            return;
        }

        Track track = simulator.getTrack();
        EnvironmentCondition weather = simulator.getEnvironmentCondition();

        double prevPos = car.getCurrentPosition();
        double accel = driver.decideAcceleration(car, track, weather);
        double deltaSpeed = accel * simulator.getTimeStep();
        double rawSpeed = car.getCurrentSpeed() + deltaSpeed;
        double newSpeed = Math.max(0.0, Math.min(rawSpeed, car.getMaxSpeed()));
        car.setCurrentSpeed(newSpeed);

        // محاسبه مسافت طی‌شده (کیلومتر)
        double distance = newSpeed * (simulator.getTimeStep() / 3600.0);
        car.setCurrentPosition(car.getCurrentPosition() + distance);

// مصرف سوخت واقعی با FuelSystem

        //اینفو هایدینگ
        double fuelUsed = simulator
                .getFuelSystem()                                 // دریافت سیستم سوخت
                .calculateFuelUsage(car, distance, accel);       // فاصله و شتاب را بده
        car.consumeFuel(fuelUsed);


        if (car.getFuelLevel() <= 0.0) {
            System.out.printf("[%.1fs] %s ❌🔋 OUT OF FUEL! Eliminated.%n",
                    eventTime + simulator.getTimeStep(), car.getModel());
            simulator.eliminateCar(car);
            return;
        }

        System.out.printf("[%.1fs] %s → speed: %.2f km/h, pos: %.2f km, fuel: %.2f%n",
                eventTime + simulator.getTimeStep(),
                car.getModel(), newSpeed, car.getCurrentPosition(), car.getFuelLevel());

        // بررسی پایان مسیر
        if (car.getCurrentPosition() >= track.getFinishLine()) {
            simulator.addEvent(new FinishEvent(eventTime + simulator.getTimeStep(), car, driver));
            return;
        }

        // بررسی پیت استاپ
        double nextPit = track.getNextPitStopPosition(prevPos);
        // بررسی پیچ
        double nextCurve = track.getNextCurvePosition(prevPos);
        // بررسی و اجرای نزدیک‌ترین رویداد
        double eventPos = Math.min(nextPit, nextCurve);
        if (prevPos < eventPos && car.getCurrentPosition() >= eventPos) {
            if (nextPit <= nextCurve) {
                simulator.addEvent(new PitStopEvent(eventTime + simulator.getTimeStep(), car, driver, nextPit));
            } else {
                simulator.addEvent(new CurveEvent(eventTime + simulator.getTimeStep(), car, driver, nextCurve));
            }
            return;
        }



        simulator.addEvent(new AccelerateEvent(eventTime + simulator.getTimeStep(), car, driver));
    }
}
