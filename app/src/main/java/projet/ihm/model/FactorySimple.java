package projet.ihm.model;

import projet.ihm.model.incident.Accident;
import projet.ihm.model.incident.Danger;
import projet.ihm.model.incident.Incident;
import projet.ihm.model.incident.Police;
import projet.ihm.model.incident.Pothole;
import projet.ihm.model.incident.RoadClosed;
import projet.ihm.model.incident.TrafficJam;
import projet.ihm.model.incident.TypeIncident;
import projet.ihm.model.incident.Worksite;

public class FactorySimple extends Factory {
    @Override
    public Incident buildIncident(TypeIncident typeIncident, Community community, String description) throws Throwable {
        switch(typeIncident) {
            case ACCIDENT:
                return new Accident(community, description);
            case DANGER:
                return new Danger(community, description);
            case POLICE:
                return new Police(community, description);
            case POTHOLE:
                return new Pothole(community, description);
            case WORKSITE:
                return new Worksite(community, description);
            case TRAFFIC_JAM:
                return new TrafficJam(community, description);
            case ROAD_CLOSED:
                return new RoadClosed(community, description);
            default:
                throw new Throwable("incident not made");
        }
    }
}
