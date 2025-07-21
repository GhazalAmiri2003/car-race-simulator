
package carrace.core;

import carrace.cars.Car;
import carrace.driver.Driver;

public class StartEvent extends RaceEvent {
    private final Car car;
    private final Driver driver;

    public StartEvent(double eventTime, Car car, Driver driver) {
        super(eventTime);
        this.car = car;
        this.driver = driver;
    }
//    This loop creates and schedules an AccelerateEvent
//    for every car-driver pair at the start of the race.
//    It's essential because without this, cars wouldn't start moving
//    so it initiates the entire simulation loop.



    @Override
    public void execute(RaceSimulator simulator) {
        System.out.printf("[%.1fs] Race started for %s%n", eventTime, car.getModel());
        // برای همه شرکت‌کننده‌ها AccelerateEvent بذار
        for (int i = 0; i < simulator.getCars().size(); i++) {
            Car c = simulator.getCars().get(i);
            Driver d = simulator.getDrivers().get(i);
            simulator.addEvent(new AccelerateEvent(eventTime, c, d));
        }
    }
}

