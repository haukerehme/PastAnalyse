package de.hrs.model;

import java.sql.Timestamp;

/**
 * Created by hrs on 11.07.16.
 */
public class EurusdDiff {
    int diff;
    Timestamp timestamp;
    boolean tradeable;

    public EurusdDiff(int diff, Timestamp timestamp, boolean tradeable) {
        this.diff = diff;
        this.timestamp = timestamp;
        this.tradeable = tradeable;
    }

    public EurusdDiff() {
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isTradeable() {
        return tradeable;
    }

    public void setTradeable(boolean tradeable) {
        this.tradeable = tradeable;
    }
}
