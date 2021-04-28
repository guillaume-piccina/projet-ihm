package projet.ihm.model.incident;

import projet.ihm.model.Community;

public class TrafficJam extends Incident {
    public TrafficJam(Community community, String description) {
        super(TypeIncident.TRAFFIC_JAM, community, description);
    }
}
