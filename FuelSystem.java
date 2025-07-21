// src/carrace/fuel/FuelSystem.java
package carrace.fuel;

import carrace.cars.Car;
import carrace.track.EnvironmentCondition;
import carrace.config.Constants;
public class FuelSystem {
    /**
     * محاسبه مصرف سوخت بر حسب لیتر
     */

    public double calculateFuelUsage(Car car, double distanceKm, double accel) {
        // نرخ پایه مصرف (لیتر بر کیلومتر) — عدد را می‌توانید در Constants تنظیم کنید
        double baseRate = Constants.FUEL_CONSUMPTION_RATE;

        // فاکتور نوع خودرو: Electric=1.5, Formula=2.0, Truck=2.5
        double vehicleFactor = car.getFuelFactor();

        // تاثیر شتاب: مصرف متناسب با مجذور شتاب افزایش می‌یابد
        double accelImpact   = 1 + Math.pow(Math.abs(accel), 1.5);

        // ضریب شرایط آب‌وهوایی (اگر می‌خواهید از آن استفاده کنید،
        // می‌توانید آن را از EnvironmentCondition بگیرید و به متد پاس بدهید)
        double weatherFactor = 1.0;

        // نرخ نهایی مصرف به ازای هر کیلومتر
        double ratePerKm = baseRate
                * vehicleFactor
                * accelImpact
                * weatherFactor;

        //  با افزایش
        //  مصرف کل = نرخ × مسافت
        return ratePerKm * distanceKm * 4;
    }



    public void refuel(Car car) {
        car.refuel();
    }
}
