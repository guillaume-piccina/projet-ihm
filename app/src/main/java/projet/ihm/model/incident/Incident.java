package projet.ihm.model.incident;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

import projet.ihm.model.Community;

public class Incident implements Parcelable {
    public static final String INCIDENT = "incident";
    protected String type;
    protected String description;
    protected String date;
    protected String community;
    protected int numberLikes;
    protected boolean relief;

    public Incident(TypeIncident type, Community community, String description) {
        this.type = type.getName();
        this.community = community.toString();
        this.description = description;
        this.date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.numberLikes = 0;
        this.relief = false;
    }


    protected Incident(Parcel in) {
        description = in.readString();
        numberLikes = in.readInt();
        relief = in.readByte() != 0;
        date = in.readString();
        type = in.readString();
        community = in.readString();
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

    public void like() {
        ++numberLikes;
    }

    public void dislike() {
        --numberLikes;
    }

    public String getName() {
        return type;
    }

    public boolean getRelief() { return this.relief; }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return this.date;
    }

    public String getCommunity() {
        return community;
    }

    public void setRelief(boolean relief) {
        this.relief = relief;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(numberLikes);
        dest.writeByte((byte) (relief ? 1 : 0));
        dest.writeString(date);
        dest.writeString(type);
        dest.writeString(community);
    }
}
