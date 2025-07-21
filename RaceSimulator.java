// ✅ RaceSimulator.java
package carrace.core;
import carrace.config.Constants;
import carrace.cars.Car;
import carrace.driver.Driver;
import carrace.fuel.FuelSystem;
import carrace.player.PlayerData;
import carrace.track.EnvironmentCondition;
import carrace.track.Track;

import java.util.*;

public class RaceSimulator {
    private final Track track;
    private final EnvironmentCondition environment;
    private final EventQueue eventQueue;
    private final boolean hardMode;
    private final String playerId;
    private final Car playerCar;
    private final FuelSystem fuelSystem;
    private double currentTime = 0.0;

    private final List<Car> cars = new ArrayList<>();
    private final List<Driver> drivers = new ArrayList<>();

    private final Map<Car, Double> finishTimes = new HashMap<>();
    private final Set<Car> eliminatedCars = new HashSet<>();

    public RaceSimulator(Track track,
                         EnvironmentCondition environment,
                         List<Car> initialCars,
                         List<Driver> initialDrivers,
                         boolean hardMode,
                         String playerId,
                         Car playerCar) {
        this.track = track;
        this.environment = environment;
        this.eventQueue = new EventQueue();
        this.hardMode = hardMode;
        this.playerId = playerId;
        this.playerCar = playerCar;
        this.fuelSystem = new FuelSystem();//کتمپوزیشن
        cars.addAll(initialCars);
        drivers.addAll(initialDrivers);
    }

    public boolean isEliminated(Car car) {
        return eliminatedCars.contains(car);
    }

    public void eliminateCar(Car car) {
        if (isEliminated(car)) return;
        eliminatedCars.add(car);

        int index = cars.indexOf(car);
        if (index >= 0) {
            cars.remove(index);
            drivers.remove(index);
            System.out.printf("[%.1fs] Car %s eliminated from race.%n", currentTime, car.getModel());
        }
    }

    public void initOpponents(boolean hardMode) {
        if (!hardMode) {
            cars.add(new carrace.cars.ElectricCar("AI-E1"));
            drivers.add(new carrace.driver.Driver(4, new carrace.driver.AggressiveStrategy()));
            cars.add(new carrace.cars.FormulaCar("AI-E2"));
            drivers.add(new carrace.driver.Driver(4, new carrace.driver.ConservativeStrategy()));
        } else {
            cars.add(new carrace.cars.FormulaCar("AI-H1"));
            drivers.add(new carrace.driver.Driver(8, new carrace.driver.AggressiveStrategy()));
            cars.add(new carrace.cars.Truck("AI-H2"));
            drivers.add(new carrace.driver.Driver(8, new carrace.driver.ConservativeStrategy()));
        }
    }

    public void startRace() {
        for (int i = 0; i < cars.size(); i++) {
            eventQueue.addEvent(new StartEvent(0.0, cars.get(i), drivers.get(i)));
        }

        while (!eventQueue.isEmpty()) {
            RaceEvent event = eventQueue.getNextEvent();
            currentTime = event.getEventTime();
            event.execute(this);
        }

        displayResults();
    }

    public void recordFinish(Car car, double time) {
        finishTimes.put(car, time);
        eliminateCar(car);
    }

    public void addEvent(RaceEvent event) {
        eventQueue.addEvent(event);
    }

    public Track getTrack() { return track; }
    public EnvironmentCondition getEnvironmentCondition() { return environment; }



    public double getTimeStep() {
        return Constants.TIME_STEP;
    }

    public List<Car> getCars() { return Collections.unmodifiableList(cars); }
    public List<Driver> getDrivers() { return Collections.unmodifiableList(drivers); }
    public FuelSystem getFuelSystem() {
        return fuelSystem;
    }

    private void displayResults() {
        System.out.println("=== Race Finished! Standings: ===");

        String[] medals = {"🥇", "🥈", "🥉"};
        final int[] index = {0};

        finishTimes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> {
                    String medal = (index[0] < medals.length) ? medals[index[0]++] : "   ";
                    System.out.printf("%s %s — Time: %.1fs%n", medal, e.getKey().getModel(), e.getValue());
                });

        Car winner = finishTimes.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (winner != null && winner == playerCar) {
            int reward = hardMode ? 2000 : 1000;
            System.out.println("\n🎉 You won! Reward: +" + reward);
            updateBudget(playerId, reward);
        }

        displayAverageFinishTime();
    }

    private void displayAverageFinishTime() {
        if (finishTimes.isEmpty()) return;
        double totalTime = finishTimes.values().stream().mapToDouble(Double::doubleValue).sum();
        double avg = totalTime / finishTimes.size();
        System.out.printf("📊 Average Finish Time: %.2f seconds%n", avg);
    }

    public void updateBudget(String playerId, int reward) {
        PlayerData player = PlayerData.load(playerId);
        player.addBudget(reward);
        player.save();
    }
}

