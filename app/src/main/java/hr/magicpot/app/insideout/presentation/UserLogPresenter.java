package hr.magicpot.app.insideout.presentation;

import java.util.List;

import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 12.9.2017..
 */

public interface UserLogPresenter {

    interface OnUserLogEvent {
        void onMessageEvent(String message);
        void onLogListFeched(List<UserLog> list);
    }

    void fechedUserLogData();

    void exportUserLogData();
}
