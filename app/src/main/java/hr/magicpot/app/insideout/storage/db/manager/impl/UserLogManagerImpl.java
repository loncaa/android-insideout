package hr.magicpot.app.insideout.storage.db.manager.impl;

import android.os.Handler;
import android.os.Looper;

import java.sql.SQLException;
import java.util.List;

import hr.magicpot.app.insideout.MainApplication;
import hr.magicpot.app.insideout.storage.db.DBHelper;
import hr.magicpot.app.insideout.storage.db.manager.UserLogManager;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 5.9.2017..
 */

public class UserLogManagerImpl implements UserLogManager{
    private final DBHelper helper;
    private final Handler handler;

    public UserLogManagerImpl() {
        this.helper = MainApplication.getDbHelper();
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void store(final UserLog model, final onDatabaseConnection listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            helper.getUserLogDao().createOrUpdate(model);
                            listener.onStoreSuccess(model);
                        } catch (SQLException e) {
                            listener.onMessage("Storing data failed.");
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void fetchAll(final onDatabaseConnection listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<UserLog> lists  = helper.getUserLogDao().queryForAll();
                            listener.onFetchAllSuccess(lists);
                        } catch (SQLException e) {
                            listener.onMessage("Fetching data failed.");
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
}
