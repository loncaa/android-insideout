package hr.magicpot.app.insideout.storage.db.interactor.impl;

import java.util.Date;
import java.util.List;

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

    public UserLogInteractorImpl() {
        this.userLogManager = new UserLogManagerImpl();
    }

    @Override
    public UserLog store(UserLog userLog) {
        return userLogManager.store(userLog);
    }

    @Override
    public void fetchAll(UserLogPresenter.OnUserLogEvent event) { userLogManager.fetchAllAsync(event); }

    @Override
    public UserLog updateLast(Date end) {
        return userLogManager.updateLast(end);
    }
}
