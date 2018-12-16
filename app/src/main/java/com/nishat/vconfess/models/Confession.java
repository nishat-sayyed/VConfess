package com.nishat.vconfess.models;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Nishat on 9/20/2017.
 */

public class Confession {

    private String confession;
    private String confessionType;
    private long timestamp;
    private String tag;

    public Confession() {
    }

    public Confession(String confession) {
        this.confession = confession;
    }

    public Confession(String confession, String confessionType, long timestamp, String tag) {
        this.confession = confession;
        this.confessionType = confessionType;
        this.timestamp = timestamp;
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getConfession() {
        return confession;
    }

    public void setConfession(String confession) {
        this.confession = confession;
    }

    public String getConfessionType() {
        return confessionType;
    }

    public void setConfessionType(String confessionType) {
        this.confessionType = confessionType;
    }
}
