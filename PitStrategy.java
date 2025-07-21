// ✅ PitStrategy.java
//تصمیم‌گیری برای پیت استاپ" رو به عنوان یک قرارداد جدا (interface) به نام PitStrategy تعریف کردم تا اگر در آینده استراتژی کاملاً متفاوتی بخوام اضافه کنم که هیچ ار
// تباطی با منطق این کلاس نداشته باشه، بتونم به راحتی اون رو اضافه کنم
package carrace.driver;

import carrace.cars.Car;
import carrace.track.EnvironmentCondition;
import carrace.track.Track;

public interface PitStrategy {
    boolean shouldPit(Car car, Track track, EnvironmentCondition weather,Driver driver, double distanceToFinish);
}