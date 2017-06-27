package cl.cutiko.recorderdemo.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class PlayerService extends Service {

    private final IBinder binder = new LocalBinder();
    private MediaPlayer mediaPlayer = new MediaPlayer();

    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void play(String path) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class LocalBinder extends Binder {

        public PlayerService getService() {
            return PlayerService.this;
        }

    }

}
