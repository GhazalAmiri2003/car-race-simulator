package carrace.core;

import carrace.cars.Car;
import carrace.driver.Driver;

public class FinishEvent extends RaceEvent {
    private final Car car;
    private final Driver driver;

    public FinishEvent(double eventTime, Car car, Driver driver) {
        super(eventTime);
        this.car = car;
        this.driver = driver;
    }

    @Override
    public void execute(RaceSimulator simulator) {
        if (simulator.isEliminated(car)) return;

        String icon = (car.getModel().contains("Electric")) ? "🚗🏁" :
                (car.getModel().contains("Formula")) ? "🏎🏁" :
                        (car.getModel().contains("Truck")) ? "🚛🏁" : "🏁";


        if (!simulator.getCars().contains(car)) return;

        double finishTime = eventTime;
        System.out.printf("[%.1fs] %s FINISHED the race %s🏁 at %.2f km%n",
                finishTime, car.getModel(), icon, car.getCurrentPosition());
        simulator.recordFinish(car, finishTime);
        }

    }