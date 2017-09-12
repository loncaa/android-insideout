package hr.magicpot.app.insideout.storage.db.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Antonio on 5.9.2017..
 */
@DatabaseTable(tableName = "LOCATION")
public class Location {
    @DatabaseField(columnName = "id", generatedId = true, dataType = DataType.INTEGER) private int id;
    @DatabaseField(columnName = "lng", dataType = DataType.DOUBLE) private double lng;
    @DatabaseField(columnName = "lat", dataType = DataType.DOUBLE) private double lat;

    public Location() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return Double.compare(location.lng, lng) == 0 && Double.compare(location.lat, lat) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lng);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
