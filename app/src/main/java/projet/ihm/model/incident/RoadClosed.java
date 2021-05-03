package projet.ihm.model.incident;

import projet.ihm.model.Community;
import projet.ihm.model.Position;

public class RoadClosed extends Incident {
    public RoadClosed(Community community, String description, Position position) {
        super(TypeIncident.ROAD_CLOSED, community, description, position);
    }
}
