package projet.ihm.model;

import android.os.Parcel;
import android.os.Parcelable;

public enum DistanceNotif implements Parcelable {
    CLOSE("50 m", 50),
    MEDIUM("100 m", 100),
    FAR("1 km", 1000);

    private String name;
    private int distance;

    DistanceNotif(String name, int distance){
        this.distance=distance;
        this.name=name;
    }

    DistanceNotif(Parcel in) {
        name = in.readString();
        distance = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DistanceNotif> CREATOR = new Creator<DistanceNotif>() {
        @Override
        public DistanceNotif createFromParcel(Parcel in) {
            return DistanceNotif.values()[in.readInt()];
        }

        @Override
        public DistanceNotif[] newArray(int size) {
            return new DistanceNotif[size];
        }
    };

    @Override
    public String toString(){
        return name;
    }

    public String getName(){
        return this.name;
    }
}
