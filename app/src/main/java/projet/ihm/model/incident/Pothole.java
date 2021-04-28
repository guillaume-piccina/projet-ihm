package projet.ihm.model.incident;

import projet.ihm.model.Community;

public class Pothole extends Incident {
    public Pothole(Community community, String description) {
        super(TypeIncident.POTHOLE, community, description);
    }
}
