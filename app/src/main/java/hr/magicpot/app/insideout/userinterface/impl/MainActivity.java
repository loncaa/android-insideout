package hr.magicpot.app.insideout.userinterface.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import hr.magicpot.app.insideout.R;
import hr.magicpot.app.insideout.presentation.impl.MainPresenterImpl;
import hr.magicpot.app.insideout.userinterface.MainView;

public class MainActivity extends AppCompatActivity implements MainView {
    private MainPresenterImpl mainPresenter;

    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.setMapFragment(R.id.map);

        button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.processResetButton();

                button.setEnabled(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logs:
                Intent myIntent = new Intent(MainActivity.this, UserLogActivityImpl.class);
                this.startActivity(myIntent);
                break;
            case R.id.settings:
                Intent i = new Intent(this, MyPreferencesActivity.class);
                this.startActivity(i);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResetButton() {
        button.show();
    }

    @Override
    public void hideResetButton() {
        button.hide();
    }

    @Override
    public void showSettingsAlert(){
        mainPresenter.showSettingsDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.processResetButton();
    }
}
