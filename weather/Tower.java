package weather;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import aircrafts.Flyable;
import static utils.Logger.log;

public abstract class Tower {
    private List<Flyable> observers = new CopyOnWriteArrayList<>();

    public void register(Flyable flyable) {
        observers.add(flyable);
        log("Tower says: " + flyable.getName() + " registered to weather tower.");
    }

    public void unregister(Flyable flyable) {
        int index = observers.indexOf(flyable);
        if (index >= 0) {
            observers.remove(flyable);
            log("Tower says: " + flyable.getName() + " unregistered from weather tower");
        } else {
            log("Flyable " + flyable.toString() + " not registered.");
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