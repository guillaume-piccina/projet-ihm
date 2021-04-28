package projet.ihm.model.incident;

import projet.ihm.model.Community;

public class Police extends Incident {
    public Police(Community community, String description) {
        super(TypeIncident.POLICE, community, description);
    }
}
