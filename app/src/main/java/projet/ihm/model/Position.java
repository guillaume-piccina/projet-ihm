package projet.ihm.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Position implements Parcelable {
    double longitude;
    double latitude;


    public Position(double longitude, double latitude){
        this.longitude=longitude;
        this.latitude=latitude;
    }


    protected Position(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<Position> CREATOR = new Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel in) {
            return new Position(in);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };

    public Position() {

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }
}
