package projet.ihm.model.incident;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import projet.ihm.model.Community;

public class Incident implements Parcelable {
    private TypeIncident type;
    private String description;
    private Date date;
    private Community community;
    private int numberLikes;
    private boolean relief;

    public Incident(TypeIncident type, Community community, String description) {
        this.type = type;
        this.community = community;
        this.description = description;
        this.date = new Date();
        this.numberLikes = 0;
        this.relief = false;
    }

    protected Incident(Parcel in) {
        description = in.readString();
        numberLikes = in.readInt();
        relief = in.readByte() != 0;
    }

    public static final Creator<Incident> CREATOR = new Creator<Incident>() {
        @Override
        public Incident createFromParcel(Parcel in) {
            return new Incident(in);
        }

        @Override
        public Incident[] newArray(int size) {
            return new Incident[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(numberLikes);
        dest.writeByte((byte) (relief ? 1 : 0));
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
