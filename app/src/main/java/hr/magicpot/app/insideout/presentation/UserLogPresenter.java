package hr.magicpot.app.insideout.presentation;

import java.util.List;

import hr.magicpot.app.insideout.storage.db.model.UserLog;

public interface UserLogPresenter {

    interface OnUserLogEvent {
        void onMessageEvent(String message);
        void onLogListFeched(List<UserLog> list);
    }

    void fechedUserLogData();

    void exportUserLogData();
}
