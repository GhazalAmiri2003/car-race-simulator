package carrace.player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {
    private static final String FILE_PATH = "budgets.txt";

    private final String playerId;
    private int budget;

    public PlayerData(String playerId, int budget) {
        this.playerId = playerId;
        this.budget = budget;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getBudget() {
        return budget;
    }

    public void addBudget(int amount) {
        this.budget += amount;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    // ذخیره بودجه در فایل (تمام بازیکن‌ها)
    public void save() {
        Map<String, Integer> allBudgets = loadAllBudgets();  // همه‌ی بودجه‌ها رو از فایل می‌خونه
        allBudgets.put(playerId, budget);                    // بودجه‌ی خود این بازیکن رو آپدیت می‌کنه
        saveAllBudgets(allBudgets);                          // کل لیست رو دوباره توی فایل می‌نویسه
    }

    // بارگذاری بودجه یک بازیکن بر اساس id
    public static PlayerData load(String playerId) {
        Map<String, Integer> allBudgets = loadAllBudgets();   // کل فایل رو می‌خونه
        int budget = allBudgets.getOrDefault(playerId, 500);  // اگه ID نبود → 500 واحد بودجه بده
        return new PlayerData(playerId, budget);
    }


    // بارگذاری بودجه همه بازیکن‌ها از فایل
    private static Map<String, Integer> loadAllBudgets() {
        Map<String, Integer> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String id = parts[0].trim();
                    int budget = Integer.parseInt(parts[1].trim());
                    map.put(id, budget);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading budgets from " + FILE_PATH);
            // اگر فایل وجود نداشت یا خطا داشت، بودجه پیش‌فرض برگردانده می‌شود
        }
        return map;
    }

    // ذخیره بودجه همه بازیکن‌ها در فایل
    private static void saveAllBudgets(Map<String, Integer> allBudgets) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {//متد رایت در فایل
            for (Map.Entry<String, Integer> entry : allBudgets.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving budgets to " + FILE_PATH);
        }
    }
}

