package jbourlet.app.tournamentcreator.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import jbourlet.app.tournamentcreator.R;
import jbourlet.app.tournamentcreator.models.ListTournament;
import jbourlet.app.tournamentcreator.models.Tournament;

public class HomeActivity extends AppCompatActivity {

    private Button mFolderButton;
    private Button mSettingsButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFolderButton   = findViewById(R.id.folder_button);
        mSettingsButton = findViewById(R.id.settings_button);

        mFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FolderActivity.class);
                startActivity(intent);
            }
        });

        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
