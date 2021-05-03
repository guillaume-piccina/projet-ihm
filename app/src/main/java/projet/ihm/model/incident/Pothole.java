package projet.ihm.model.incident;

import projet.ihm.model.Community;
import projet.ihm.model.Position;

public class Pothole extends Incident {
    public Pothole(Community community, String description, Position position) {
        super(TypeIncident.POTHOLE, community, description,position);
    }
}
