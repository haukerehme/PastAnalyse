package de.hrs.Rechner;

import de.hrs.dao.TradeMessageDao;
import de.hrs.model.TradeMessage;
import de.hrs.model.Tradevorhersage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by hrs on 21.06.16.
 */
@Component
public class AnalyseMehereVergleichsstrecken {

    @Resource
    TradeMessageDao tradeMessageDao;

    public static Logger log = LoggerFactory.getLogger(AnalyseMehereVergleichsstrecken.class);
    RechnerZusammenfasser rechner;
    List<Integer> closewerte;
    Timestamp now;
    int ausgangspkt, vergleichsLaenge, auswertungslaenge;
    boolean longPosition, mehrereVergleichsstrecken, SimulatorModus;
    int zusammenfasserInterval;
    int spread;
    String instrument;
    List<Integer> listVergleichsLaenge;

    public AnalyseMehereVergleichsstrecken() {
    }

    public AnalyseMehereVergleichsstrecken(Timestamp now, List<Integer> intArray, int ausgangspkt, List<Integer> listVergleichsLaenge, int auswertungslaenge, int spread, String instrument) {
        closewerte = intArray;
        this.now = now;
        this.ausgangspkt = ausgangspkt;
        this.listVergleichsLaenge = listVergleichsLaenge;
        this.auswertungslaenge = auswertungslaenge;
        this.spread = spread;
        this.instrument = instrument;
    }

    public void run() {
        Tradevorhersage tradevorhersageGes;
        Tradevorhersage tradeTmp;
        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size() - 1, 240, auswertungslaenge, 30, spread, "EUR/USD", true, false);
        tradevorhersageGes = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);

        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size() - 1, 210, auswertungslaenge, 30, spread, "EUR/USD", true, false);
        tradeTmp = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);
        tradevorhersageGes.addiere(tradeTmp);

        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size() - 1, 180, auswertungslaenge, 20, spread, "EUR/USD", true, false);
        tradeTmp = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);
        tradevorhersageGes = tradevorhersageGes.addiere(tradeTmp);

        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size() - 1, 150, auswertungslaenge, 10, spread, "EUR/USD", true, false);
        tradeTmp = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);
        tradevorhersageGes = tradevorhersageGes.addiere(tradeTmp);

        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size() - 1, 120, auswertungslaenge, 10, spread, "EUR/USD", true, false);
        tradeTmp = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);
        tradevorhersageGes = tradevorhersageGes.addiere(tradeTmp);
//        log.info(" Formation " + tradevorhersageGes.getAnzFormFound() + " mal gefunden");

        TradeMessage tradeMessage = new TradeMessage();//(now, "EUR/USD", auswertungslaenge,
        // tradevorhersageGes.getAnzFormFound(),
        // tradevorhersageGes.getGewinnzaehlerLong(),
        // tradevorhersageGes.getMittlererLongGewinn(),
        // tradevorhersageGes.getHoherLongGewinn(),
        // tradevorhersageGes.getSehrHoherLongGewinn(),
        // tradevorhersageGes.getVerlustzaehlerLong(),
        // tradevorhersageGes.getHoherLongVerlust(),
        // tradevorhersageGes.getGeringerShortGewinn(),
        // tradevorhersageGes.getMittlererShortGewinn(),
        // tradevorhersageGes.getHoherShortGewinn(),
        // tradevorhersageGes.getSehrHoherShortGewinn(),
        // tradevorhersageGes.getVerlustzaehlerShort(),
        // tradevorhersageGes.getHoherShortVerlust());
        tradeMessage.setTimestamp(now);
        tradeMessage.setInstrument("EUR/USD");
        tradeMessage.setTimeperiod(auswertungslaenge);
        tradeMessage.setAnzFound(tradevorhersageGes.getAnzFormFound());
        tradeMessage.setLongWin(tradevorhersageGes.getGewinnzaehlerLong());
        tradeMessage.setLongWinMiddle(tradevorhersageGes.getMittlererLongGewinn());
        tradeMessage.setLongWinHigh(tradevorhersageGes.getHoherLongGewinn());
        tradeMessage.setLongWinVeryHigh(tradevorhersageGes.getSehrHoherLongGewinn());
        tradeMessage.setLongLose(tradevorhersageGes.getVerlustzaehlerLong());
        tradeMessage.setLongLoseHigh(tradevorhersageGes.getHoherLongVerlust());
        tradeMessage.setShortWin(tradevorhersageGes.getGeringerShortGewinn());
        tradeMessage.setShortWinMiddle(tradevorhersageGes.getMittlererShortGewinn());
        tradeMessage.setShortWinHigh(tradevorhersageGes.getHoherShortGewinn());
        tradeMessage.setShortWinVeryHigh(tradevorhersageGes.getSehrHoherShortGewinn());
        tradeMessage.setShortLose(tradevorhersageGes.getVerlustzaehlerShort());
        tradeMessage.setShortLoseHigh(tradevorhersageGes.getHoherShortVerlust());
        if(tradeMessageDao == null){
            log.error("tradeMessageDao = null");
        }else{
            tradeMessageDao.persist(tradeMessage);
        }
    }

    public TradeMessageDao getTradeMessageDao() {
        return tradeMessageDao;
    }

    public void setTradeMessageDao(TradeMessageDao tradeMessageDao) {
        this.tradeMessageDao = tradeMessageDao;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        AnalyseMehereVergleichsstrecken.log = log;
    }

    public RechnerZusammenfasser getRechner() {
        return rechner;
    }

    public void setRechner(RechnerZusammenfasser rechner) {
        this.rechner = rechner;
    }

    public List<Integer> getClosewerte() {
        return closewerte;
    }

    public void setClosewerte(List<Integer> closewerte) {
        this.closewerte = closewerte;
    }

    public Timestamp getNow() {
        return now;
    }

    public void setNow(Timestamp now) {
        this.now = now;
    }

    public int getAusgangspkt() {
        return ausgangspkt;
    }

    public void setAusgangspkt(int ausgangspkt) {
        this.ausgangspkt = ausgangspkt;
    }

    public int getVergleichsLaenge() {
        return vergleichsLaenge;
    }

    public void setVergleichsLaenge(int vergleichsLaenge) {
        this.vergleichsLaenge = vergleichsLaenge;
    }

    public int getAuswertungslaenge() {
        return auswertungslaenge;
    }

    public void setAuswertungslaenge(int auswertungslaenge) {
        this.auswertungslaenge = auswertungslaenge;
    }

    public boolean isLongPosition() {
        return longPosition;
    }

    public void setLongPosition(boolean longPosition) {
        this.longPosition = longPosition;
    }

    public boolean isMehrereVergleichsstrecken() {
        return mehrereVergleichsstrecken;
    }

    public void setMehrereVergleichsstrecken(boolean mehrereVergleichsstrecken) {
        this.mehrereVergleichsstrecken = mehrereVergleichsstrecken;
    }

    public boolean isSimulatorModus() {
        return SimulatorModus;
    }

    public void setSimulatorModus(boolean simulatorModus) {
        SimulatorModus = simulatorModus;
    }

    public int getZusammenfasserInterval() {
        return zusammenfasserInterval;
    }

    public void setZusammenfasserInterval(int zusammenfasserInterval) {
        this.zusammenfasserInterval = zusammenfasserInterval;
    }

    public int getSpread() {
        return spread;
    }

    public void setSpread(int spread) {
        this.spread = spread;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public List<Integer> getListVergleichsLaenge() {
        return listVergleichsLaenge;
    }

    public void setListVergleichsLaenge(List<Integer> listVergleichsLaenge) {
        this.listVergleichsLaenge = listVergleichsLaenge;
    }
}
