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
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        dbHelper = new DBHelper(this);
        context = getApplicationContext();
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static SharedPreferences getSharedPref(String name, int mode){
        return context.getApplicationContext().getSharedPreferences(name, mode);
    }
}
