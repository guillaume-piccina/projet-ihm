package projet.ihm.model.incident;

public class Accident extends Incident {
    public Accident(String description) {
        super(TypeIncident.ACCIDENT, description);
    }
}
