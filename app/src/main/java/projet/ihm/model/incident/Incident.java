package projet.ihm.model.incident;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

import projet.ihm.model.Position;
import projet.ihm.model.Community;

public class Incident implements Parcelable {
    public static final String INCIDENT = "incident";
    protected String type;
    protected String description;
    protected String date;
    protected String community;
    protected int numberLikes;
    protected boolean relief;
    protected Position position;

    public Incident(TypeIncident type, Community community, String description, Position position) {
        this.type = type.getName();
        this.community = community.toString();
        this.description = description;
        this.date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.numberLikes = 0;
        this.relief = false;
        this.position=position;
    }


    protected Incident(Parcel in) {
        type = in.readString();
        description = in.readString();
        date = in.readString();
        community = in.readString();
        numberLikes = in.readInt();
        relief = in.readByte() != 0;
        position = in.readParcelable(Position.class.getClassLoader());
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

    public String getType() {
        return type;
    }

    public Position getPosition(){ return position;}

    public LatLng getPositiontoLatLng(){
        return new LatLng(position.getLatitude(),position.getLongitude());
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
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(community);
        dest.writeInt(numberLikes);
        dest.writeByte((byte) (relief ? 1 : 0));
        dest.writeParcelable(position, flags);
    }
}
