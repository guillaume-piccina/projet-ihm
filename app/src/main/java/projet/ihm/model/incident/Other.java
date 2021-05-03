package projet.ihm.model.incident;

import projet.ihm.model.Community;
import projet.ihm.model.Position;

public class Other extends Incident {
    public Other(TypeIncident type, Community community, String description, Position position) {
        super(type, community, description, position);
    }
}
