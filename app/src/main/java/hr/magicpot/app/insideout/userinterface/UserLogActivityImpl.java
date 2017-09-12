package hr.magicpot.app.insideout.userinterface;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import hr.magicpot.app.insideout.R;
import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.presentation.UserLogPresenterImpl;
import hr.magicpot.app.insideout.storage.db.model.UserLog;
import hr.magicpot.app.insideout.userinterface.adapter.UserLogListAdapter;

public class UserLogActivityImpl extends ListActivity implements UserLogView {

    UserLogPresenter userLogPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs_list);

        userLogPresenter = new UserLogPresenterImpl(this);

        userLogPresenter.fechedUserLogData();
    }

    @Override
    public void onlogListFeched(List<UserLog> list) {
        UserLogListAdapter adapter = new UserLogListAdapter(this, R.layout.list_item, list);
        setListAdapter(adapter);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
