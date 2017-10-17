package hr.magicpot.app.insideout.storage.db.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "USERLOG")
public class UserLog {
    @DatabaseField(generatedId = true, dataType = DataType.INTEGER) private int id;
    @DatabaseField(columnName = "start", dataType = DataType.DATE) private Date start;
    @DatabaseField(columnName = "end", dataType = DataType.DATE) private Date end;

    public UserLog() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
