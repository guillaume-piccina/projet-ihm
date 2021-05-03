package projet.ihm.model.incident;

import projet.ihm.model.Community;
import projet.ihm.model.Position;

public class Danger extends Incident {
    public Danger(Community community, String description, Position position) {
        super(TypeIncident.DANGER, community, description, position);
    }
}
