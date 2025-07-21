// ✅ RaceStrategy.java
package carrace.driver;

import carrace.cars.Car;
import carrace.track.EnvironmentCondition;
import carrace.track.Track;

public interface RaceStrategy {
    /**
     * تصمیم‌گیری برای شتاب‌گیری (acceleration)
     */
    double decideAcceleration(Car car, Track track, EnvironmentCondition weather, Driver driver);

    /**
     * تصمیم‌گیری برای کاهش سرعت در پیچ‌ها
     */
    double decideBrakeFactor(Car car, Track track, EnvironmentCondition weather, Driver driver, double distanceToCurve);

    /**
     * ضریب خطای انسانی بر اساس نوع استراتژی (محافظه‌کارانه / تهاجمی)
     */
    double getErrorMultiplier();
}
