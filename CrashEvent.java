// src/carrace/core/CrashEvent.java
package carrace.core;

import carrace.cars.Car;
import carrace.driver.Driver;

/**
 * رویداد تصادف در مسابقه
 */
public class CrashEvent extends RaceEvent {
    private final Car car;
    private final Driver driver;
    private final String crashType;

    /**
     * سازنده سه‌پارامتری: اگر crashType ندادید، از "minor" استفاده می‌کند
     */
    public CrashEvent(double eventTime, Car car, Driver driver) {
        this(eventTime, car, driver, "minor");
    }

    /**
     * سازنده اصلی که نوع تصادف را هم می‌گیرد
     * @param crashType "minor", "moderate" یا "major"
     */
    public CrashEvent(double eventTime, Car car, Driver driver, String crashType) {
        super(eventTime);
        this.car = car;
        this.driver = driver;
        this.crashType = crashType;
    }

    @Override
    public void execute(RaceSimulator simulator) {
        if (simulator.isEliminated(car)) return;

        // سرعت صفر می‌شود
        car.setCurrentSpeed(0.0);

        int delay;
        switch (crashType.toLowerCase()) {
            case "minor":
                delay = 20;
                break;
            case "moderate":
                delay = 60;
                break;
            case "major":
                System.out.printf("[%.1fs] %s 💥 MAJOR CRASH! Removed from race.%n",
                        eventTime, car.getModel());
                simulator.eliminateCar(car);
                return;
            default:
                delay = 0;
        }

        System.out.printf("[%.1fs] %s 🛑 CRASH (%s) — delay %ds%n",
                eventTime, car.getModel(), crashType, delay);

        // برنامه‌ریزی ادامه شتاب‌گیری بعد از تأخیر
        simulator.addEvent(
                new AccelerateEvent(eventTime + delay, car, driver)
        );
    }
}
