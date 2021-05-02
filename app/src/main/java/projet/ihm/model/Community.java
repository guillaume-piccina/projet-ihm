package projet.ihm.model;

import android.os.Parcel;
import android.os.Parcelable;

public enum Community implements Parcelable
{
    EVERYBODY("Tout le monde"),
    MOTORIST("Automobiliste"),
    BIKER("Motard"),
    TRUCK_DRIVER("Camionneur"),
    CYCLIST("Cycliste"),
    POLICEMAN("Policier"),
    AMBULANCE_DRIVER("Ambulancier"),
    PEDESTRIAN("Piéton");

    private String name;

    Community(String name) {
        this.name = name;
    }

    Community(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ordinal());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Community> CREATOR = new Creator<Community>() {
        @Override
        public Community createFromParcel(Parcel in) {
            return Community.values()[in.readInt()];
        }

        @Override
        public Community[] newArray(int size) {
            return new Community[size];
        }
    };

    @Override
    public String toString(){
        return name;
    }
}
