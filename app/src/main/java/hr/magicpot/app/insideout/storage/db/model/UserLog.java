package hr.magicpot.app.insideout.storage.db.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Antonio on 5.9.2017..
 */
@DatabaseTable(tableName = "USERLOG")
public class UserLog {
    @DatabaseField(generatedId = true, dataType = DataType.INTEGER) private int id;
    @DatabaseField(dataType = DataType.DATE) private Date created;
    @DatabaseField(dataType = DataType.DOUBLE) private double timeInside;

    public UserLog() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public double getTimeInside() {
        return timeInside;
    }

    public void setTimeInside(double timeInside) {
        this.timeInside = timeInside;
    }
}
