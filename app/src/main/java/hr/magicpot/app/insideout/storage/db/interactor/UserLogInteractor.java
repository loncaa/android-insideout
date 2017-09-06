package hr.magicpot.app.insideout.storage.db.interactor;

import java.util.List;

import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 5.9.2017..
 */

public interface UserLogInteractor {

    interface onDatabaseListener extends Interactor.onDatabaseListener {
        void onUserLogStoreSuccess(UserLog userLog);
        void onFetchSuccess(List<UserLog> userLogs);
    }

    void store(UserLog model);

    void fetchAll();
}
