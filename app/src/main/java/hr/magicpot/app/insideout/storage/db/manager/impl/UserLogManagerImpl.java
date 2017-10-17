package hr.magicpot.app.insideout.storage.db.manager.impl;

import android.os.Handler;
import android.os.Looper;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.magicpot.app.insideout.MainApplication;
import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.storage.db.DBHelper;
import hr.magicpot.app.insideout.storage.db.manager.UserLogManager;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

public class UserLogManagerImpl implements UserLogManager{
    private final DBHelper helper;
    private final Handler handler;

    public UserLogManagerImpl() {
        this.helper = MainApplication.getDbHelper();
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public UserLog store(final UserLog model) {
        Dao.CreateOrUpdateStatus status = null;
        try {
            status = helper.getUserLogDao().createOrUpdate(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(status != null && status.isCreated())
            return model;

        return null;
    }

    @Override
    public void fetchAllAsync(final UserLogPresenter.OnUserLogEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<UserLog> lists  = helper.getUserLogDao().queryForAll();
                            if(lists.size() > 0)
                                event.onLogListFeched(lists);
                            else
                                event.onMessageEvent("List is empty.");
                        } catch (SQLException e) {
                            e.printStackTrace();
                            event.onMessageEvent("Fetch db failed.");
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public List<UserLog> fetchAll() {
        try {
            return helper.getUserLogDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public UserLog setEndTime(final Date end) {
        try {
            QueryBuilder<UserLog, Integer> builder = helper.getUserLogDao().queryBuilder();
            builder.limit(1L);
            builder.orderBy("start", false);  // true for ascending, false for descending
            List<UserLog> list = helper.getUserLogDao().query(builder.prepare());  // returns list of ten items

            if(list.size() == 1){

                UserLog last = list.get(0);
                if(last.getEnd() == null) {
                    last.setEnd(end);
                    helper.getUserLogDao().createOrUpdate(last);
                    return last;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
