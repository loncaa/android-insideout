package hr.magicpot.app.insideout.userinterface;

import java.util.List;

import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 12.9.2017..
 */

public interface UserLogView {

    void onlogListFeched(List<UserLog> list);

    void showToastMessage(String message);
}
