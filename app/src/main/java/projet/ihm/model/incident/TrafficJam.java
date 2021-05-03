package projet.ihm.model.incident;

import projet.ihm.model.Community;
import projet.ihm.model.Position;

public class TrafficJam extends Incident {
    public TrafficJam(Community community, String description, Position position) {
        super(TypeIncident.TRAFFIC_JAM, community, description, position);
    }
}
