package projet.ihm.model.incident;

import projet.ihm.model.Community;
import projet.ihm.model.Position;

public class Other extends Incident {
    public Other(Community community, String description, Position position) {
        super(TypeIncident.OTHER, community, description, position);
    }
}
