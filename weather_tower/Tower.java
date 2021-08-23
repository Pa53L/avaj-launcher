package weather_tower;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import aircrafts.Flyable;

public abstract class Tower {
    private List<Flyable> observers = new CopyOnWriteArrayList<>();

    public void register(Flyable flyable) {
        observers.add(flyable);
        System.out.println("Tower registered " + flyable.getClass());
    }

    public void unregister(Flyable flyable) {
        int index = observers.indexOf(flyable);
        if (index >= 0) {
            observers.remove(flyable);
        } else {
            System.out.println("Flyable " + flyable.toString() + " not registered.");
        }
    }

    protected void conditionsChanged() {
        Iterator<Flyable> iterator = observers.listIterator();
        while (iterator.hasNext()) {
            Flyable f = iterator.next();
            f.updateConditions();
        }
    }
}