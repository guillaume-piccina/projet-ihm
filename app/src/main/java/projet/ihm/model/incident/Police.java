package projet.ihm.model.incident;

import projet.ihm.model.Community;
import projet.ihm.model.Position;

public class Police extends Incident {
    public Police(Community community, String description, Position position) {
        super(TypeIncident.POLICE, community, description, position);
    }
}
