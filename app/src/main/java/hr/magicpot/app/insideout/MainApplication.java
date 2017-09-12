package hr.magicpot.app.insideout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import hr.magicpot.app.insideout.storage.db.DBHelper;


/**
 * Created by xxx on 21.11.2016..
 */
public class MainApplication extends Application {
    private static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        dbHelper = new DBHelper(this);
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }
}
