package projet.ihm.model.incident;

import projet.ihm.model.Community;
import projet.ihm.model.Position;

public class Worksite extends Incident {
    public Worksite(Community community, String description, Position position) {
        super(TypeIncident.WORKSITE, community, description,position);
    }
}
