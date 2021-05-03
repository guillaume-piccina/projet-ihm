package projet.ihm.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

    private Community COMMUNITY;
    private DistanceNotif DISTNOTIF;

    protected Profile(Parcel in) {
        COMMUNITY = Community.values()[in.readInt()];
        DISTNOTIF = DistanceNotif.values()[in.readInt()];
    }

    public Profile(){
        this.COMMUNITY=Community.EVERYBODY;
        this.DISTNOTIF=DistanceNotif.MEDIUM;
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public Community getCOMMUNITY() {
        return COMMUNITY;
    }

    private void setCOMMUNITY(Community COMMUNITY) {
        this.COMMUNITY = COMMUNITY;
    }

    public void setCOMMUNITY(String community){
        for (Community community1 : Community.values()){
            if (community.equals(community1.toString())){
                this.setCOMMUNITY(community1);
                return;
            }
        }
    }

    public DistanceNotif getDISTNOTIF() {
        return DISTNOTIF;
    }

    private void setDISTNOTIF(DistanceNotif DISTNOTIF) {
        this.DISTNOTIF = DISTNOTIF;
    }

    public void setDISTNOTIF(String community){
        for (DistanceNotif dist : DistanceNotif.values()){
            if (community.equals(dist.toString())){
                this.setDISTNOTIF(dist);
                return;
            }
        }
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.COMMUNITY.ordinal());
        parcel.writeInt(this.DISTNOTIF.ordinal());
    }
}
