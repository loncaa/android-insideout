package hr.magicpot.app.insideout.storage.db.manager;

import java.util.Date;
import java.util.List;

import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

public interface UserLogManager {
    UserLog store(UserLog model);

    List<UserLog> fetchAll();

    void fetchAllAsync(UserLogPresenter.OnUserLogEvent event);

    UserLog setEndTime(Date end);
}
