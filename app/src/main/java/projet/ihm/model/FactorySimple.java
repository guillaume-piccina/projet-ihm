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
    protected Incident buildIncident(TypeIncident typeIncident, String description) throws Throwable {
        switch(typeIncident) {
            case ACCIDENT:
                return new Accident(description);
            case DANGER:
                return new Danger(description);
            case POLICE:
                return new Police(description);
            case POTHOLE:
                return new Pothole(description);
            case WORKSITE:
                return new Worksite(description);
            case TRAFFIC_JAM:
                return new TrafficJam(description);
            case ROAD_CLOSED:
                return new RoadClosed(description);
            default:
                throw new Throwable("incident not made");
        }
    }
}
