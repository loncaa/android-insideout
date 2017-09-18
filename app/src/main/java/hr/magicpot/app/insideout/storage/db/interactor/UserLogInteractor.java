package hr.magicpot.app.insideout.storage.db.interactor;

import java.util.Date;
import java.util.List;

import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 11.9.2017..
 */

public interface UserLogInteractor {
    interface DatabaseListener extends Interactor.onDatabaseListener {
        void onLogStoreSuccess(UserLog userLog);
        void onLogFetchSuccess(List<UserLog> userLog);
        void onUpdateSuccess(UserLog userLog);
    }

    UserLog store(UserLog userLog);

    void fetchAll(UserLogPresenter.OnUserLogEvent event);

    UserLog setEndTime(Date end);
}
