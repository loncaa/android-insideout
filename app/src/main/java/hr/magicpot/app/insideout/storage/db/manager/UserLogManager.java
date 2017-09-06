package hr.magicpot.app.insideout.storage.db.manager;

import java.util.List;

import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 5.9.2017..
 */

public interface UserLogManager {
    interface onDatabaseConnection{
        void onMessage(String msg);
        void onStoreSuccess(UserLog log);
        void onFetchAllSuccess(List<UserLog> log);
    }

    void store(UserLog model, onDatabaseConnection listener);

    void fetchAll(onDatabaseConnection listener);
}
