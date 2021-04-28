package projet.ihm.model.incident;

import projet.ihm.model.Community;

public class RoadClosed extends Incident {
    public RoadClosed(Community community, String description) {
        super(TypeIncident.ROAD_CLOSED, community, description);
    }
}
