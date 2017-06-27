package cl.cutiko.recorderdemo.models;

import com.orm.SugarRecord;

/**
 * Created by cutiko on 26-06-17.
 */

public class Track extends SugarRecord {

    private String path, name;

    public Track() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
