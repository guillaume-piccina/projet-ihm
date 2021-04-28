package projet.ihm.model;

public enum Community {
    EVERYBODY("Tout le monde"),
    MOTORIST("Automobiliste"),
    BIKER("Motard"),
    TRUCK_DRIVER("Cammionneur"),
    CYCLIST("Cycliste"),
    POLICEMAN("Policier"),
    AMBULANCE_DRIVER("Ambulancier"),
    PEDESTRIAN("Piéton");

    private String name;

    Community(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
