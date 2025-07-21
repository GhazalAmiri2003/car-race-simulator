package carrace.ui;

import carrace.cars.*;
import carrace.core.RaceSimulator;
import carrace.driver.Driver;
import carrace.setup.RaceSetup;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUI {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.print("Enter your player ID: ");
        String playerId = scanner.nextLine().trim();

        RaceSetup setup = new RaceSetup(playerId);
        System.out.println("Loaded budget: " + setup.getRemainingBudget());

        while (true) {
            System.out.println("Choose a car: ElectricCar / FormulaCar / Truck");
            String input = scanner.nextLine();
            if (setup.selectCar(input)) break;
            System.out.println("Invalid choice or insufficient budget. Try again.");
        }
        System.out.println("Remaining budget: " + setup.getRemainingBudget());
        while (true) {
            try {


            System.out.println("Choose driver level (2, 4, 6, 8, 10):");
            //تایپ کانورشن
            int level = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Aggressive? (y/n), or press Enter to skip:");
            String input = scanner.nextLine().trim();

            boolean success; // تعرسف متغیر
            if (input.isEmpty()) {
                success = setup.selectDriver(level);  // Overloaded version
            } else {
                boolean aggressive = input.equalsIgnoreCase("y");
                success = setup.selectDriver(level, aggressive);  // Full version
            }

            if (success) break;  //  خروج از حلقه در صورت انتخاب موفق
            System.out.println("❗ Invalid level or insufficient budget. Try again.");
        }catch (NumberFormatException e) {              //ارور هندل
                System.out.println("❌ Please enter a valid number.");
        } catch (IllegalArgumentException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }



        System.out.println("Remaining budget: " + setup.getRemainingBudget());

        while (true) {
            System.out.println("Choose track: city / mountain / arctic");
            String input = scanner.nextLine();
            if (setup.selectTrack(input)) break;
            System.out.println("Invalid choice or insufficient budget. Try again.");
        }
        System.out.println("Remaining budget: " + setup.getRemainingBudget());

        while (true) {
            System.out.println("Choose weather: sunny / rainy / snowy / foggy");
            String input = scanner.nextLine();
            if (setup.selectWeather(input)) break;
            System.out.println("Invalid choice or insufficient budget. Try again.");
        }
        System.out.println("Remaining budget: " + setup.getRemainingBudget());

        System.out.println("Hard mode? (y/n): ");
        boolean hardMode = scanner.nextLine().trim().equalsIgnoreCase("y");
        setup.setGameMode(hardMode);

        System.out.println("Start the race? (y to continue)");
        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("Race canceled.");
            return;
        }

        //اضافه شدن ماشینا به لیست
        List<Car> cars = new ArrayList<>();  //استفاده در RaceSimulator
        List<Driver> drivers = new ArrayList<>();
        Car playerCar = setup.getSelectedCar();
        Driver playerDriver = setup.getSelectedDriver();
        cars.add(playerCar);
        drivers.add(playerDriver);

        String icon = (playerCar instanceof ElectricCar) ? "🚗" :
                (playerCar instanceof FormulaCar) ? "🏎" :
                        (playerCar instanceof Truck) ? "🚛" : "";


        //استارت خورن سیمیوبیتور
        RaceSimulator simulator = new RaceSimulator(
                setup.getSelectedTrack(),
                setup.getSelectedEnvironment(),
                cars,
                drivers,
                setup.isHardMode(),
                playerId,
                playerCar
        );

        simulator.initOpponents(hardMode);
        simulator.startRace();

    }
}