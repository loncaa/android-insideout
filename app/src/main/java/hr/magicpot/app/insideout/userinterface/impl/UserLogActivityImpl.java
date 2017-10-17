package hr.magicpot.app.insideout.userinterface.impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import hr.magicpot.app.insideout.R;
import hr.magicpot.app.insideout.presentation.UserLogPresenter;
import hr.magicpot.app.insideout.presentation.impl.UserLogPresenterImpl;
import hr.magicpot.app.insideout.storage.db.model.UserLog;
import hr.magicpot.app.insideout.userinterface.UserLogView;
import hr.magicpot.app.insideout.userinterface.adapter.UserLogListAdapter;

public class UserLogActivityImpl extends AppCompatActivity implements UserLogView {

    UserLogPresenter userLogPresenter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_logs_list);

        userLogPresenter = new UserLogPresenterImpl(this);
        userLogPresenter.fechedUserLogData();

        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    public void onlogListFeched(List<UserLog> list) {
        UserLogListAdapter adapter = new UserLogListAdapter(this, R.layout.list_item, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu_logs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.generate_logs:
                userLogPresenter.exportUserLogData();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return true;
    }
}
