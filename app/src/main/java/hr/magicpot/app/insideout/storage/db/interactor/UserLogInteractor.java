package hr.magicpot.app.insideout.storage.db.interactor;

import android.app.Activity;

import java.util.Date;
import java.util.List;

import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

public interface UserLogInteractor {
    interface DatabaseListener extends Interactor.onDatabaseListener {
        void onLogStoreSuccess(UserLog userLog);
        void onLogFetchSuccess(List<UserLog> userLog);
        void onUpdateSuccess(UserLog userLog);
    }

    UserLog store(UserLog userLog);

    void fetchAll(UserLogPresenter.OnUserLogEvent event);

    UserLog setEndTime(Date end);

    void exportData(UserLogPresenter.OnUserLogEvent event, Activity context);
}
