package cl.cutiko.recorderdemo.views.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import cl.cutiko.recorderdemo.R;
import cl.cutiko.recorderdemo.models.Track;
import cl.cutiko.recorderdemo.views.main.recorder.RecordingDialogFragment;
import cl.cutiko.recorderdemo.views.main.tracks.TracksFragment;

public class MainActivity extends AppCompatActivity implements RecordCoordinator {

    private static final int RC_RECORDING = 343;
    private TracksFragment tracksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        tracksFragment = (TracksFragment) getSupportFragmentManager().findFragmentById(R.id.tracksFragment);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.RECORD_AUDIO};
                    requestPermissions(permissions, RC_RECORDING);
                } else {
                    displayDialog();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (RC_RECORDING == requestCode) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                displayDialog();
            } else {
                Toast.makeText(this, "Please grant permission to record audio", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("recorder");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dialogFragment = RecordingDialogFragment.newInstance();
        dialogFragment.show(ft, "recorder");
    }

    @Override
    public void addTrack(Track track) {
        tracksFragment.addTrack(track);
    }
}
