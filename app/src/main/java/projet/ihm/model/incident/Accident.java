package projet.ihm.model.incident;

import projet.ihm.model.Community;
import projet.ihm.model.Position;

public class Accident extends Incident {
    public Accident(Community community, String description, Position position) {
        super(TypeIncident.ACCIDENT, community, description, position);
    }
}
