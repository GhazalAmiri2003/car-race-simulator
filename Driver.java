// ✅ Driver.java
package carrace.driver;

import carrace.cars.Car;
import carrace.track.EnvironmentCondition;
import carrace.track.Track;

public class Driver {
    private final int skillLevel; // فقط 2، 4، 6، 8، 10
    private final RaceStrategy strategy;
    private final PitStrategy pitStrategy;


    public Driver(int skillLevel, RaceStrategy strategy) {
        if (!isValidSkillLevel(skillLevel)) {
            throw new IllegalArgumentException("Skill level must be 2, 4, 6, 8, or 10");
        }
        this.skillLevel = skillLevel;
        this.strategy = strategy;
        this.pitStrategy = selectPitStrategy(skillLevel);
    }

    private boolean isValidSkillLevel(int level) {
        return level == 2 || level == 4 || level == 6 || level == 8 || level == 10;
    }

    private PitStrategy selectPitStrategy(int level) {
        return switch (level) {
            case 2 -> new PitStrategyLevel2();
            case 4 -> new PitStrategyLevel4();
            case 6 -> new PitStrategyLevel6();
            case 8 -> new PitStrategyLevel8();
            case 10 -> new PitStrategyLevel10();
            default -> throw new IllegalArgumentException("Invalid skill level");
        };
    }

    public int getSkillLevel() {
        return skillLevel;
    }


    public RaceStrategy getStrategy() {
        return strategy;
    }

    public PitStrategy getPitStrategy() {
        return pitStrategy;
    }

    public double getErrorFactor() {
        double baseError = 1.5 - ((skillLevel * 1.4) / 10.0);
        double strategyMultiplier = strategy.getErrorMultiplier();
        if (skillLevel == 2 || skillLevel == 4) baseError += 0.4;
        return Math.random() * baseError * strategyMultiplier;
    }

    public boolean shouldPit(Car car, Track track, EnvironmentCondition weather, double distanceLeft) {
        return pitStrategy.shouldPit(car, track, weather, this, distanceLeft);
    }

    public double decideAcceleration(Car car, Track track, EnvironmentCondition weather) {
        return strategy.decideAcceleration(car, track, weather, this);
    }

    public double decideBrakeFactor(Car car, Track track, EnvironmentCondition weather, double distanceToCurve) {
        return strategy.decideBrakeFactor(car, track, weather, this, distanceToCurve);
    }
}