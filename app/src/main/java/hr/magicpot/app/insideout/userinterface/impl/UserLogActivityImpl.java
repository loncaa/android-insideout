package hr.magicpot.app.insideout.userinterface.impl;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import hr.magicpot.app.insideout.R;
import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.presentation.impl.UserLogPresenterImpl;
import hr.magicpot.app.insideout.storage.db.model.UserLog;
import hr.magicpot.app.insideout.userinterface.UserLogView;
import hr.magicpot.app.insideout.userinterface.adapter.UserLogListAdapter;

public class UserLogActivityImpl extends ListActivity implements UserLogView {

    UserLogPresenter userLogPresenter;

    FloatingActionButton exportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs_list);

        userLogPresenter = new UserLogPresenterImpl(this);

        userLogPresenter.fechedUserLogData();

        exportButton = (FloatingActionButton) findViewById(R.id.export);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLogPresenter.exportUserLogData();
            }
        });
    }

    @Override
    public void onlogListFeched(List<UserLog> list) {
        exportButton.show();

        UserLogListAdapter adapter = new UserLogListAdapter(this, R.layout.list_item, list);
        setListAdapter(adapter);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
