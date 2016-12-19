package de.hrs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by hrs on 21.06.16.
 */
@Entity(name="trademessagetest")
public class TradeMessage {

    @Id
    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = false)
    private String instrument;

    @Column(nullable = false)
    private int timeperiod;

    @Column(nullable = false)
    private int anzfound;

    @Column(nullable = false)
    private int longwin;

    @Column(nullable = false)
    private int longwinmiddle;

    @Column(nullable = false)
    private int longwinhigh;

    @Column(nullable = false)
    private int longwinveryhigh;

    @Column(nullable = false)
    private int longlose;

    @Column(nullable = false)
    private int longlosehigh;

    @Column(nullable = false)
    private int shortwin;

    @Column(nullable = false)
    private int shortwinmiddle;

    @Column(nullable = false)
    private int shortwinhigh;

    @Column(nullable = false)
    private int shortwinveryhigh;

    @Column(nullable = false)
    private int shortlose;

    @Column(nullable = false)
    private int shortlosehigh;

    @Column(nullable = true)
    private double currentvalue;

    @Column(nullable = true)
    private double twentyminfuturevalue;

    public TradeMessage() {
    }

    public TradeMessage(Timestamp timestamp, String instrument, int timeperiod, int anzfound, int longwin, int longwinmiddle, int longwinhigh, int longwinveryhigh, int longlose, int longlosehigh, int shortwin, int shortwinmiddle, int shortwinhigh, int shortwinveryhigh, int shortlose, int shortlosehigh) {
        this.timestamp = timestamp;
        this.instrument = instrument;
        this.timeperiod = timeperiod;
        this.anzfound = anzfound;
        this.longwin = longwin;
        this.longwinmiddle = longwinmiddle;
        this.longwinhigh = longwinhigh;
        this.longwinveryhigh = longwinveryhigh;
        this.longlose = longlose;
        this.longlosehigh = longlosehigh;
        this.shortwin = shortwin;
        this.shortwinmiddle = shortwinmiddle;
        this.shortwinhigh = shortwinhigh;
        this.shortwinveryhigh = shortwinveryhigh;
        this.shortlose = shortlose;
        this.shortlosehigh = shortlosehigh;
    }

    public TradeMessage(Timestamp timestamp, String instrument, int timeperiod, int anzfound, int longwin, int longwinmiddle, int longwinhigh, int longwinveryhigh, int longlose, int longlosehigh, int shortwin, int shortwinmiddle, int shortwinhigh, int shortwinveryhigh, int shortlose, int shortlosehigh, double currentvalue, double twentyminfuturevalue) {
        this.timestamp = timestamp;
        this.instrument = instrument;
        this.timeperiod = timeperiod;
        this.anzfound = anzfound;
        this.longwin = longwin;
        this.longwinmiddle = longwinmiddle;
        this.longwinhigh = longwinhigh;
        this.longwinveryhigh = longwinveryhigh;
        this.longlose = longlose;
        this.longlosehigh = longlosehigh;
        this.shortwin = shortwin;
        this.shortwinmiddle = shortwinmiddle;
        this.shortwinhigh = shortwinhigh;
        this.shortwinveryhigh = shortwinveryhigh;
        this.shortlose = shortlose;
        this.shortlosehigh = shortlosehigh;
        this.currentvalue = currentvalue;
        this.twentyminfuturevalue = twentyminfuturevalue;
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

    public int getAnzfound() {
        return anzfound;
    }

    public void setAnzfound(int anzfound) {
        this.anzfound = anzfound;
    }

    public int getLongwin() {
        return longwin;
    }

    public void setLongwin(int longwin) {
        this.longwin = longwin;
    }

    public int getLongwinmiddle() {
        return longwinmiddle;
    }

    public void setLongwinmiddle(int longwinmiddle) {
        this.longwinmiddle = longwinmiddle;
    }

    public int getLongwinhigh() {
        return longwinhigh;
    }

    public void setLongwinhigh(int longwinhigh) {
        this.longwinhigh = longwinhigh;
    }

    public int getLongwinveryhigh() {
        return longwinveryhigh;
    }

    public void setLongwinveryhigh(int longwinveryhigh) {
        this.longwinveryhigh = longwinveryhigh;
    }

    public int getLonglose() {
        return longlose;
    }

    public void setLonglose(int longlose) {
        this.longlose = longlose;
    }

    public int getLonglosehigh() {
        return longlosehigh;
    }

    public void setLonglosehigh(int longlosehigh) {
        this.longlosehigh = longlosehigh;
    }

    public int getShortwin() {
        return shortwin;
    }

    public void setShortwin(int shortwin) {
        this.shortwin = shortwin;
    }

    public int getShortwinmiddle() {
        return shortwinmiddle;
    }

    public void setShortwinmiddle(int shortwinmiddle) {
        this.shortwinmiddle = shortwinmiddle;
    }

    public int getShortwinhigh() {
        return shortwinhigh;
    }

    public void setShortwinhigh(int shortwinhigh) {
        this.shortwinhigh = shortwinhigh;
    }

    public int getShortwinveryhigh() {
        return shortwinveryhigh;
    }

    public void setShortwinveryhigh(int shortwinveryhigh) {
        this.shortwinveryhigh = shortwinveryhigh;
    }

    public int getShortlose() {
        return shortlose;
    }

    public void setShortlose(int shortlose) {
        this.shortlose = shortlose;
    }

    public int getShortlosehigh() {
        return shortlosehigh;
    }

    public void setShortlosehigh(int shortlosehigh) {
        this.shortlosehigh = shortlosehigh;
    }

    public double getCurrentvalue() {
        return currentvalue;
    }

    public void setCurrentvalue(double currentvalue) {
        this.currentvalue = currentvalue;
    }

    public double getTwentyminfuturevalue() {
        return twentyminfuturevalue;
    }

    public void setTwentyminfuturevalue(double twentyminfuturevalue) {
        this.twentyminfuturevalue = twentyminfuturevalue;
    }
}
