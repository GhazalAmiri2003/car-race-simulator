package carrace.track;

public class EnvironmentCondition {
    private final WeatherType weatherType;
    private double weatherRisk;       // ضریب ریسک هر آب‌وهوا (برای محاسبه احتمال تصادف)
    private double tractionModifier;  // ضریب چسبندگی جاده برای تاثیر بر پایداری و عملکرد ماشین

    public EnvironmentCondition(WeatherType weatherType) {
        this.weatherType = weatherType;
        switch (weatherType) {
            case SUNNY:

                this.weatherRisk = 0.5;
                this.tractionModifier = 1.0;
                break;
            case RAINY:
                this.weatherRisk = 0.7;
                this.tractionModifier = 0.8;
                break;
            case SNOWY:
                this.weatherRisk = 0.9;
                this.tractionModifier = 0.6;    //friction
                break;
            case FOGGY:
                this.weatherRisk = 1;
                this.tractionModifier = 0.7;
                break;
        }
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public double getWeatherRisk() {
        return weatherRisk;
    }

    public double getTractionModifier() {
        return tractionModifier;
    }

    @Override
    public String toString() {
        return "Weather: " + weatherType + " (Risk: " + weatherRisk + ", Traction: " + tractionModifier + ")";
    }
}
