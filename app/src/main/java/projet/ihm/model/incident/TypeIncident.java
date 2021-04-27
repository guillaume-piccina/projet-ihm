package projet.ihm.model.incident;

public enum TypeIncident {
    ACCIDENT("Accident"),
    DANGER("Danger"),
    ROAD_CLOSED("Route fermée"),
    TRAFFIC_JAM("Trafic ralenti"),
    WORKSITE("Travaux"),
    POLICE("Police"),
    POTHOLE("Nid de poule");

    private String name;

    TypeIncident(String name) {}

    public String getName() {
        return name;
    }
}
