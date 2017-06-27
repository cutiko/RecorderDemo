package cl.cutiko.recorderdemo.data;

import java.util.List;

import cl.cutiko.recorderdemo.models.Track;

/**
 * Created by cutiko on 26-06-17.
 */

public class Queries {

    public List<Track> tracks() {
        return Track.listAll(Track.class);
    }

}
