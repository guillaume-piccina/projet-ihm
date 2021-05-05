package projet.ihm.model;

import projet.ihm.model.incident.Accident;
import projet.ihm.model.incident.Danger;
import projet.ihm.model.incident.Incident;
import projet.ihm.model.incident.Other;
import projet.ihm.model.incident.Police;
import projet.ihm.model.incident.Pothole;
import projet.ihm.model.incident.RoadClosed;
import projet.ihm.model.incident.TrafficJam;
import projet.ihm.model.incident.TypeIncident;
import projet.ihm.model.incident.Worksite;

public class FactorySimple extends Factory {
    @Override
    public Incident buildIncident(TypeIncident typeIncident, Community community, String description, Position position) throws Throwable {
        switch(typeIncident) {
            case ACCIDENT:
                return new Accident(community, description, position);
            case DANGER:
                return new Danger(community, description, position);
            case POLICE:
                return new Police(community, description, position);
            case POTHOLE:
                return new Pothole(community, description, position);
            case WORKSITE:
                return new Worksite(community, description, position);
            case TRAFFIC_JAM:
                return new TrafficJam(community, description, position);
            case ROAD_CLOSED:
                return new RoadClosed(community, description, position);
            case OTHER:
                    return new Other(community, description, position);
            default:
                throw new Throwable("incident not made");
        }
    }
}
