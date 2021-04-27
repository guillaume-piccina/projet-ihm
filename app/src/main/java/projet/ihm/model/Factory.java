package projet.ihm.model;

import projet.ihm.model.incident.Incident;
import projet.ihm.model.incident.TypeIncident;

public abstract class Factory {

    protected abstract Incident buildIncident(TypeIncident typeIncident, String description) throws Throwable;
}
