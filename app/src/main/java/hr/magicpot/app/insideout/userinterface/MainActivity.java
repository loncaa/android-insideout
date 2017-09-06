package hr.magicpot.app.insideout.userinterface;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import hr.magicpot.app.insideout.R;
import hr.magicpot.app.insideout.presentation.MainPresenterImpl;

public class MainActivity extends AppCompatActivity implements MainView{
    private MainPresenterImpl mainPresenter;

    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.processResetButton();
                button.setEnabled(true);
            }
        });

        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.setMapFragment(R.id.map);
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
}
