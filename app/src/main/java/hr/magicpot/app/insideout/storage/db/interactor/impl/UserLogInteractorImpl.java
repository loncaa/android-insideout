package hr.magicpot.app.insideout.storage.db.interactor.impl;

import android.app.Activity;

import java.util.Date;
import java.util.List;

import hr.magicpot.app.insideout.UserLogExporter;
import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.storage.db.interactor.UserLogInteractor;
import hr.magicpot.app.insideout.storage.db.manager.UserLogManager;
import hr.magicpot.app.insideout.storage.db.manager.impl.UserLogManagerImpl;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

public class UserLogInteractorImpl implements UserLogInteractor{
    private final UserLogManager userLogManager;
    private final UserLogExporter exporter;

    public UserLogInteractorImpl() {

        this.userLogManager = new UserLogManagerImpl();
        this.exporter = new UserLogExporter();
    }

    @Override
    public UserLog store(UserLog userLog) {
        return userLogManager.store(userLog);
    }

    @Override
    public void fetchAll(UserLogPresenter.OnUserLogEvent event) { userLogManager.fetchAllAsync(event); }

    @Override
    public UserLog setEndTime(Date end) {
        return userLogManager.setEndTime(end);
    }

    @Override
    public void exportData(final UserLogPresenter.OnUserLogEvent event, final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<UserLog> logs = userLogManager.fetchAll();
                if(logs.size() > 0) {
                    String message = exporter.export(logs, activity);
                    event.onMessageEvent(message);
                }
                else
                    event.onMessageEvent("Log list is empty.");
            }
        });
    }
}
