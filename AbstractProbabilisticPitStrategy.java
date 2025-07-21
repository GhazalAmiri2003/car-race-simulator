// ✅ AbstractProbabilisticPitStrategy.java
package carrace.driver;

import carrace.cars.Car;
import carrace.config.Constants;
import carrace.track.EnvironmentCondition;
import carrace.track.Track;

public abstract class AbstractProbabilisticPitStrategy implements PitStrategy {
    private final double correctDecisionChance;

    public AbstractProbabilisticPitStrategy(double correctDecisionChance) {
        this.correctDecisionChance = correctDecisionChance;
    }
    public double getCorrectDecisionChance() {
        return correctDecisionChance;
    }

    @Override
    public boolean shouldPit(Car car,
                             Track track,
                             EnvironmentCondition weather,
                             Driver driver,
                             double distanceToFinish) {
        // 1) فاصله تا پایان (کیلومتر)
        double distToFinish = Math.max(0.0, track.getFinishLine() - car.getCurrentPosition());
        //مصرف پایه برای تمام مسیر با فرض یک نرخ ثابت
        double requiredFuelToFinish = distToFinish
                * Constants.FUEL_CONSUMPTION_RATE
                * car.getFuelFactor();
        // 3) بافر ایمنی (مثلاً ۱۰٪ از مصرف مورد نیاز)
        double buffer = requiredFuelToFinish * 0.10;
        double threshold = requiredFuelToFinish + buffer;
        // 4) تصمیم ایده‌آل: اگر سوخت کمتر یا مساوی این حد باشد => باید پیت کند
        boolean idealDecision = car.getFuelLevel() <= threshold;
        if (car.getFuelLevel() <= 5) { idealDecision = true; }
        if (car.getFuelLevel() > 55) { idealDecision = false; }

        // 5) اعمال احتمال خطا: correctDecisionChance از استراتژی راننده
        if (Math.random() < getCorrectDecisionChance()) {
            return idealDecision;    // با احتمال صحیح تصمیم می‌گیرد
        } else {
            return !idealDecision;   // در موارد خطا برعکس عمل می‌کند
        }
    }


}
