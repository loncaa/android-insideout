package hr.magicpot.app.insideout;

import android.app.Application;

import hr.magicpot.app.insideout.storage.db.DBHelper;

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
