package hr.magicpot.app.insideout.userinterface;

import java.util.List;

import hr.magicpot.app.insideout.storage.db.model.UserLog;

public interface UserLogView {

    void onlogListFeched(List<UserLog> list);

    void showToastMessage(String message);
}
