package carrace.setup;

import carrace.cars.*;
import carrace.driver.*;
import carrace.player.PlayerData;
import carrace.track.*;
import carrace.player.BudgetManager;
public class RaceSetup {
    private final String playerId;
    private int budget;


    private Car selectedCar;
    private Driver selectedDriver;
    private Track selectedTrack;
    private EnvironmentCondition selectedEnvironment;
    private boolean hardMode;

    public RaceSetup(String playerId) {
                this.playerId = playerId;
                this.budget   = BudgetManager.getBudget(playerId);
           }

    /**
     * انتخاب ماشین از بین گزینه‌های ElectricCar, FormulaCar, Truck
     */
    public boolean selectCar(String key) {
        Car car = switch (key.toLowerCase()) {
            case "electriccar" -> new ElectricCar("ElectricCar");
            case "formulacar"  -> new FormulaCar("FormulaCar");
            case "truck"       -> new Truck("Truck");
            default -> null;
        };
        if (car == null || budget < car.getCost()) return false;
        selectedCar = car;
        budget -= car.getCost();
        BudgetManager.updateBudget(playerId, -car.getCost());
        return true;
    }

    /**
     * انتخاب راننده با سطح مهارت مشخص و استراتژی محتاط یا تهاجمی
     */
    public boolean selectDriver(int level, boolean aggressive) {
        if (level != 2 && level != 4 && level != 6 && level != 8 && level != 10) return false;

        int cost = switch (level) {
            case 2 -> 0;
            case 4 -> 200;
            case 6 -> 400;
            case 8 -> 500;
            case 10 -> 800;
            default -> 0;
        };


        if (budget < cost) return false;

        selectedDriver = new Driver(
                level,
                aggressive ? new AggressiveStrategy() : new ConservativeStrategy()
        );
        budget -= cost;
        BudgetManager.updateBudget(playerId, -cost);
        return true;
    }
    //اورلود برای جلو گیری از خطا و سختگیری کمتر و ساخت دیفالت
    public boolean selectDriver(int level) {
        return selectDriver(level, false);  // false → ConservativeStrategy
    }


    /**
     * انتخاب مسیر مسابقه از بین City / Mountain / Arctic
     */
    public boolean selectTrack(String key) {
        TrackType tt = switch (key.toLowerCase()) {
            case "city"     -> TrackType.CITY;
            case "mountain" -> TrackType.MOUNTAIN;
            case "arctic"   -> TrackType.ARCTIC;
            default         -> null;
        };

        if (tt == null) return false;

        int cost = switch (tt) {
            case CITY     -> 0;
            case MOUNTAIN -> 200;
            case ARCTIC   -> 150;
        };

        if (budget < cost) return false;

        selectedTrack = new Track(tt);
        budget -= cost;
        BudgetManager.updateBudget(playerId, -cost);
        return true;
    }

    /**
     * انتخاب شرایط آب‌وهوایی
     */
    public boolean selectWeather(String key) {
        WeatherType wt = switch (key.toLowerCase()) {
            case "sunny" -> WeatherType.SUNNY;
            case "rainy" -> WeatherType.RAINY;
            case "snowy" -> WeatherType.SNOWY;
            case "foggy" -> WeatherType.FOGGY;
            default      -> null;
        };

        if (wt == null) return false;

        int cost = switch (wt) {
            case SUNNY -> 0;
            case RAINY -> 100;
            case SNOWY -> 150;
            case FOGGY -> 80;
        };

        if (budget < cost) return false;

        selectedEnvironment = new EnvironmentCondition(wt);
        budget -= cost;
        BudgetManager.updateBudget(playerId, -cost);
        return true;

    }


    //تایین حالت بازی

    public void setGameMode(boolean hard) {
        this.hardMode = hard;
    }

    // ==== Getters ====
    /**
     * تعیین حالت بازی استفاده شده در ui
     */
    public int getRemainingBudget() {
        return budget;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    public Track getSelectedTrack() {
        return selectedTrack;
    }

    public EnvironmentCondition getSelectedEnvironment() {
        return selectedEnvironment;
    }

    public boolean isHardMode() {
        return hardMode;
    }
}


