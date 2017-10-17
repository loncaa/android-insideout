package hr.magicpot.app.insideout.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import hr.magicpot.app.insideout.storage.db.model.Location;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "insideout.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Location, Integer> locationDao;
    private Dao<UserLog, Integer> userLogDao;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Location.class);
            TableUtils.createTable(connectionSource, UserLog.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Location.class, false);
            TableUtils.clearTable(connectionSource, Location.class);

            TableUtils.dropTable(connectionSource, UserLog.class, false);
            TableUtils.clearTable(connectionSource, UserLog.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Location, Integer> getLocationDao(){
        if(locationDao == null)
            try {
                locationDao = getDao(Location.class);
                return locationDao;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return locationDao;
    }

    public Dao<UserLog, Integer> getUserLogDao(){
        if(userLogDao == null)
            try {
                userLogDao = getDao(UserLog.class);
                return userLogDao;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return userLogDao;
    }
}
