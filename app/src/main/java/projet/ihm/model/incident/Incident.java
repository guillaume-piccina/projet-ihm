package projet.ihm.model.incident;

import java.util.Date;

public class Incident {
    private TypeIncident type;
    private String description;
    private Date date;
    private int numberLikes;
    private boolean relief;

    public Incident(TypeIncident type, String description) {
        this.type = type;
        this.description = description;
        this.date = new Date();
        this.numberLikes = 0;
        this.relief = false;
    }

    public void like() {
        ++numberLikes;
    }

    public void dislike() {
        --numberLikes;
    }

    public String getName() {
        return type.getName();
    }

    public void setRelief(boolean relief) {
        this.relief = relief;
    }
}
