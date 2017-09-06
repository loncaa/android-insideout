package hr.magicpot.app.insideout.storage.db.interactor.impl;

import java.util.List;

import hr.magicpot.app.insideout.storage.db.interactor.UserLogInteractor;
import hr.magicpot.app.insideout.storage.db.manager.UserLogManager;
import hr.magicpot.app.insideout.storage.db.manager.impl.UserLogManagerImpl;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 5.9.2017..
 */

public class UserLogInteractorImpl implements UserLogInteractor, UserLogManager.onDatabaseConnection {
    private final UserLogManager userLogManager;
    private final onDatabaseListener checkListener;

    public UserLogInteractorImpl(UserLogInteractor.onDatabaseListener checkListener) {
        this.userLogManager = new UserLogManagerImpl();
        this.checkListener = checkListener;
    }

    //IN
    @Override
    public void store(UserLog model) {
        userLogManager.store(model, this);
    }

    @Override
    public void fetchAll() {
        userLogManager.fetchAll(this);
    }

    //OUT
    @Override
    public void onMessage(String msg) {
        checkListener.onMessage(msg);
    }

    @Override
    public void onStoreSuccess(UserLog log) {

    }

    @Override
    public void onFetchAllSuccess(List<UserLog> logs) {

    }
}
