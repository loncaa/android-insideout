package hr.magicpot.app.insideout.storage.db.interactor.impl;

import java.util.Date;
import java.util.List;

import hr.magicpot.app.insideout.UserLogExporter;
import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.storage.db.interactor.UserLogInteractor;
import hr.magicpot.app.insideout.storage.db.manager.UserLogManager;
import hr.magicpot.app.insideout.storage.db.manager.impl.UserLogManagerImpl;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 11.9.2017..
 */

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
    public void exportData(UserLogPresenter.OnUserLogEvent event) {

        List<UserLog> logs = userLogManager.fetchAll();
        String message = exporter.export(logs);
        event.onMessageEvent(message);
    }
}
