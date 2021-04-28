package projet.ihm.model.incident;

import projet.ihm.model.Community;

public class Accident extends Incident {
    public Accident(Community community, String description) {
        super(TypeIncident.ACCIDENT, community, description);
    }
}
