package projet.ihm.model.incident;

import projet.ihm.model.Community;

public class Danger extends Incident {
    public Danger(Community community, String description) {
        super(TypeIncident.DANGER, community, description);
    }
}
