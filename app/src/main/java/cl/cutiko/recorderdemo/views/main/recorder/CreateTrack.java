package cl.cutiko.recorderdemo.views.main.recorder;

import cl.cutiko.recorderdemo.models.Track;

/**
 * Created by cutiko on 26-06-17.
 */

public class CreateTrack {

    private RecordingCallback callback;


    public CreateTrack(RecordingCallback callback) {
        this.callback = callback;
    }

    public void saveToDb(String path) {
        Track track = new Track();
        track.setPath(path);

        long count = Track.count(Track.class);
        track.setName("Recording " + count++);
        track.save();

        callback.done(track);
    }

}
