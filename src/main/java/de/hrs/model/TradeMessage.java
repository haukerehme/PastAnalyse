package de.hrs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by hrs on 21.06.16.
 */
@Entity
public class TradeMessage {

    @Id
    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = false)
    private String instrument;

    @Column(nullable = false)
    private int timeperiod;

    @Column(nullable = false)
    private int anzFound;

    @Column(nullable = false)
    private int longWin;

    @Column(nullable = false)
    private int longWinMiddle;

    @Column(nullable = false)
    private int longWinHigh;

    @Column(nullable = false)
    private int longWinVeryHigh;

    @Column(nullable = false)
    private int longLose;

    @Column(nullable = false)
    private int longLoseHigh;

    @Column(nullable = false)
    private int shortWin;

    @Column(nullable = false)
    private int shortWinMiddle;

    @Column(nullable = false)
    private int shortWinHigh;

    @Column(nullable = false)
    private int shortWinVeryHigh;

    @Column(nullable = false)
    private int shortLose;

    @Column(nullable = false)
    private int shortLoseHigh;

    public TradeMessage() {
    }

    public TradeMessage(Timestamp timestamp, String instrument, int timeperiod, int anzFound, int longWin, int longWinMiddle, int longWinHigh, int longWinVeryHigh, int longLose, int longLoseHigh, int shortWin, int shortWinMiddle, int shortWinHigh, int shortWinVeryHigh, int shortLose, int shortLoseHigh) {
        this.timestamp = timestamp;
        this.instrument = instrument;
        this.timeperiod = timeperiod;
        this.anzFound = anzFound;
        this.longWin = longWin;
        this.longWinMiddle = longWinMiddle;
        this.longWinHigh = longWinHigh;
        this.longWinVeryHigh = longWinVeryHigh;
        this.longLose = longLose;
        this.longLoseHigh = longLoseHigh;
        this.shortWin = shortWin;
        this.shortWinMiddle = shortWinMiddle;
        this.shortWinHigh = shortWinHigh;
        this.shortWinVeryHigh = shortWinVeryHigh;
        this.shortLose = shortLose;
        this.shortLoseHigh = shortLoseHigh;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public int getTimeperiod() {
        return timeperiod;
    }

    public void setTimeperiod(int timeperiod) {
        this.timeperiod = timeperiod;
    }

    public int getAnzFound() {
        return anzFound;
    }

    public void setAnzFound(int anzFound) {
        this.anzFound = anzFound;
    }

    public int getLongWin() {
        return longWin;
    }

    public void setLongWin(int longWin) {
        this.longWin = longWin;
    }

    public int getLongWinMiddle() {
        return longWinMiddle;
    }

    public void setLongWinMiddle(int longWinMiddle) {
        this.longWinMiddle = longWinMiddle;
    }

    public int getLongWinHigh() {
        return longWinHigh;
    }

    public void setLongWinHigh(int longWinHigh) {
        this.longWinHigh = longWinHigh;
    }

    public int getLongWinVeryHigh() {
        return longWinVeryHigh;
    }

    public void setLongWinVeryHigh(int longWinVeryHigh) {
        this.longWinVeryHigh = longWinVeryHigh;
    }

    public int getLongLose() {
        return longLose;
    }

    public void setLongLose(int longLose) {
        this.longLose = longLose;
    }

    public int getLongLoseHigh() {
        return longLoseHigh;
    }

    public void setLongLoseHigh(int longLoseHigh) {
        this.longLoseHigh = longLoseHigh;
    }

    public int getShortWin() {
        return shortWin;
    }

    public void setShortWin(int shortWin) {
        this.shortWin = shortWin;
    }

    public int getShortWinMiddle() {
        return shortWinMiddle;
    }

    public void setShortWinMiddle(int shortWinMiddle) {
        this.shortWinMiddle = shortWinMiddle;
    }

    public int getShortWinHigh() {
        return shortWinHigh;
    }

    public void setShortWinHigh(int shortWinHigh) {
        this.shortWinHigh = shortWinHigh;
    }

    public int getShortWinVeryHigh() {
        return shortWinVeryHigh;
    }

    public void setShortWinVeryHigh(int shortWinVeryHigh) {
        this.shortWinVeryHigh = shortWinVeryHigh;
    }

    public int getShortLose() {
        return shortLose;
    }

    public void setShortLose(int shortLose) {
        this.shortLose = shortLose;
    }

    public int getShortLoseHigh() {
        return shortLoseHigh;
    }

    public void setShortLoseHigh(int shortLoseHigh) {
        this.shortLoseHigh = shortLoseHigh;
    }
}
