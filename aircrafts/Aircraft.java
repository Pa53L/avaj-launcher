package aircrafts;

public abstract class Aircraft {
    protected long id;
    protected String name;
    protected Coordinates coordinates;
    private static long idCounter = 1;
    
    protected Aircraft(String name, Coordinates coordinates) {
        this.name = name;
        if (coordinates.getHeight() > 100) {
            coordinates = new Coordinates(
                coordinates.getLongitude(), coordinates.getLatitude(), 100);
        }
        this.coordinates = coordinates;
        this.id = nextId();
    }

    private long nextId() {
        return id++;
    }
}