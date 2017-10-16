package hr.magicpot.app.insideout.presentation.impl;

import java.util.List;

import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.storage.db.interactor.UserLogInteractor;
import hr.magicpot.app.insideout.storage.db.interactor.impl.UserLogInteractorImpl;
import hr.magicpot.app.insideout.storage.db.model.UserLog;
import hr.magicpot.app.insideout.userinterface.UserLogView;

/**
 * Created by Antonio on 12.9.2017..
 */
public class UserLogPresenterImpl implements UserLogPresenter, UserLogPresenter.OnUserLogEvent {
    private final UserLogInteractor userLogInteractor;
    private final UserLogView activity;

    public UserLogPresenterImpl(UserLogView activity) {
        this.activity = activity;
        userLogInteractor = new UserLogInteractorImpl();
    }

    @Override
    public void onMessageEvent(String message) {
        this.activity.showToastMessage(message);
    }

    @Override
    public void onLogListFeched(List<UserLog> list) {
        activity.onlogListFeched(list);
    }

    @Override
    public void fechedUserLogData() {
        userLogInteractor.fetchAll(this);
    }

    @Override
    public void exportUserLogData() {
        userLogInteractor.exportData(this);
    }
}
