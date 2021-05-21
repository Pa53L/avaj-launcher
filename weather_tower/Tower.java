package weather_tower;
import java.util.ArrayList;

import aircrafts.Flyable;

public abstract class Tower {
    private ArrayList<Flyable> observers = new ArrayList<>();

    public void register(Flyable flyable) {
        observers.add(flyable);
        System.out.println("Tower gegistered " + flyable.getClass());
    }

    public void unregister(Flyable flyable) {
        try {
            observers.remove(flyable);
        } catch (Exception e) {
            System.out.println("Flyable " + flyable.toString() + " not registered.");
            e.printStackTrace();
        }
    }

    protected void conditionsChanged() {
        for (Flyable f : observers) {
            f.updateConditions();
        }
    }
}