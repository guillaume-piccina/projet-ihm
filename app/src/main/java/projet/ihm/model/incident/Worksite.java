package projet.ihm.model.incident;

import projet.ihm.model.Community;

public class Worksite extends Incident {
    public Worksite(Community community, String description) {
        super(TypeIncident.WORKSITE, community, description);
    }
}
