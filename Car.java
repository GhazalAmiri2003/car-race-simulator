package carrace.cars;

import carrace.track.EnvironmentCondition;
import carrace.track.WeatherType;
import java.util.Map;


public abstract class Car {
    private final String model;
    private final double maxSpeed;
    private final double stability;
    private final double fuelCapacity;
    private double fuelLevel;
    private final int cost;
    private final Map<WeatherType, Double> weatherAdaptationMap;
    private double currentSpeed  = 0.0;
    private double currentPosition = 0.0;


    public Car(String model,
               double maxSpeed,
               double stability,
               double fuelCapacity,
               int cost,
               Map<WeatherType, Double> weatherAdaptationMap)
                {
        this.model                = model;
        this.maxSpeed             = maxSpeed;
        this.stability            = stability;
        this.fuelCapacity         = fuelCapacity;
        this.fuelLevel            = fuelCapacity;
        this.cost                 = cost;
        this.weatherAdaptationMap = weatherAdaptationMap;

    }

    // --- Getters ---
    public String getModel()            { return model; }
    public double getMaxSpeed()         { return maxSpeed; }

    public double getFuelCapacity()     { return fuelCapacity; }
    public double getFuelLevel()        { return fuelLevel; }
    public int    getCost()             { return cost; }

    public double getCurrentSpeed()     { return currentSpeed; }
    public void   setCurrentSpeed(double s)   { this.currentSpeed = s; }

    public double getCurrentPosition()  { return currentPosition; }
    public void   setCurrentPosition(double p) { this.currentPosition = p; }

    // سازگاری با شرایط آب‌و‌هوا
    public double getWeatherAdaptationFactor(WeatherType wt) {
        return weatherAdaptationMap.getOrDefault(wt, 1.0);
    }
    public double getAdjustedStability(EnvironmentCondition env) {
        //اینفو هایدینگ
        return stability
                * env.getTractionModifier()
                * getWeatherAdaptationFactor(env.getWeatherType());
    }

    // سوخت
    public void consumeFuel(double amount) {
        fuelLevel = Math.max(0.0, fuelLevel - amount);
    }
    public void refuel() {
        fuelLevel = fuelCapacity;
    }









//    I didn't hardcode those values in the superclass because using
//    abstract or overridable methods gives me polymorphic behavior and makes each subclass
//    responsible for its own logic — even if it's simple now.

    // متدهای انتزاعی که زیرکلاس‌ها باید پیاده کنند

    public abstract double getFuelFactor();

    // پیت‌استاپ
    public abstract int getRefuelTime();

    //used in accelerate event

    public abstract double getAcceleration();
}


