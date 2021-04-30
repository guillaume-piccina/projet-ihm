package projet.ihm.model;

public enum DistanceNotif {
    CLOSE("50 m", 50),
    MEDIUM("100 m", 100),
    FAR("1 km", 1000);

    private String name;
    private int distance;

    DistanceNotif(String name, int distance){
        this.distance=distance;
        this.name=name;
    }
    @Override
    public String toString(){
        return name;
    }

    public String getName(){
        return this.name;
    }
}
