package de.hrs.Rechner;

import de.hrs.model.TradeMessage;
import de.hrs.model.Tradevorhersage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrs on 21.06.16.
 */
@Component
public class AnalyseMehereVergleichsstrecken implements Runnable {




    TradeMessage tradeMessage;

    public AnalyseMehereVergleichsstrecken() {
    }

    public static Logger log = LoggerFactory.getLogger(AnalyseMehereVergleichsstrecken.class);
    RechnerZusammenfasser rechner;
    List<Integer> closewerte;
    Timestamp now;
    int ausgangspkt,vergleichsLaenge,auswertungslaenge;
    boolean longPosition, mehrereVergleichsstrecken, SimulatorModus;
    int zusammenfasserInterval;
    int spread;
    String instrument;

    int GewinnzaehlerLong = 0;
    int VerlustzaehlerLong = 0;

    int GewinnzaehlerShort = 0;
    int VerlustzaehlerShort = 0;

    int GenerellPlus = 0;
    int GenerellMinus = 0;
    int hohesMinus = 0;
    int hohesPlus  = 0;

    int hoherLongVerlust = 0;
    int geringerLongGewinn = 0;
    int mittlererLongGewinn = 0;
    int hoherLongGewinn = 0;
    int sehrHoherLongGewinn = 0;

    int geringerShortGewinn = 0;
    int mittlererShortGewinn = 0;
    int hoherShortGewinn = 0;
    int sehrHoherShortGewinn = 0;
    int hoherShortVerlust = 0;
    int anzFormFound = 0;

    public AnalyseMehereVergleichsstrecken(Timestamp now, List<Integer> intArray,int ausgangspkt,int auswertungslaenge, int spread, String instrument){
        closewerte = intArray;
        this.now = now;
        this.ausgangspkt =ausgangspkt;
        this.auswertungslaenge = auswertungslaenge;
        this.spread = spread;
        this.instrument = instrument;
    }

    void addiere(Tradevorhersage tmp){
        GewinnzaehlerLong += tmp.getGewinnzaehlerLong();
        VerlustzaehlerLong += tmp.getVerlustzaehlerLong();

        GewinnzaehlerShort += tmp.getGewinnzaehlerShort();
        VerlustzaehlerShort += tmp.getVerlustzaehlerShort();

        GenerellPlus += tmp.getGenerellPlus();
        GenerellMinus += tmp.getGenerellMinus();
        hohesMinus += tmp.getHohesMinus();
        hohesPlus  += tmp.getHohesPlus();

        hoherLongVerlust += tmp.getHoherLongVerlust();
        geringerLongGewinn += tmp.getGeringerLongGewinn();
        mittlererLongGewinn += tmp.getMittlererLongGewinn();
        hoherLongGewinn += tmp.getHoherLongGewinn();
        sehrHoherLongGewinn += tmp.getSehrHoherLongGewinn();

        geringerShortGewinn += tmp.getGeringerShortGewinn();
        mittlererShortGewinn += tmp.getMittlererShortGewinn();
        hoherShortGewinn += tmp.getHoherShortGewinn();
        sehrHoherShortGewinn += tmp.getSehrHoherShortGewinn();
        hoherShortVerlust += tmp.getHoherShortVerlust();
        anzFormFound += tmp.getAnzFormFound();
    }

    public void run(){

        //Hier ArrayList<RechnerZusammenFasser> deklarieren
        //Jeder Thread bekommt eine Sublist von closewerte und
        //Die ArrayList<RechnerZusammenFasser> wird in einer for-each Schleife durchlaufen.
        //Wenn bei allen Threads thread.join erfolgreich, dann Auswertung

        ArrayList<RechnerZusammenfasser> listRechner = new ArrayList<>();
        ArrayList<Thread> listThread = new ArrayList<>();

        int[] vergleichslaengen = {240,210,180,150,120};
        int[] zusammenfasserInterval = {30,30,20,10,10};
        int threadPaare = 10;

        // - auswertungslaenge, weil die auswertungslaenge eh übergeben wird
        // - 240 da das längste Muster abgezogen werden muss
        int blockgroesse = (this.closewerte.size() - (auswertungslaenge + 240)) / threadPaare;
        for (int i = 0; i < threadPaare ; i++){
            //List<Integer> historie = new ArrayList<>(this.closewerte.subList(i * blockgroesse,((i + 1) * blockgroesse) - 1 + auswertungslaenge));
            List<Integer> historie = new ArrayList<>();
            for (int z = i * blockgroesse; z <  ((i + 1) * blockgroesse) - 1 + auswertungslaenge; z++){
                historie.add(closewerte.get(z));
            }

            for(int j = 0; j < vergleichslaengen.length;j++){
                List<Integer> muster = new ArrayList<>();
                for (int z = this.closewerte.size()-(vergleichslaengen[j]+1); z < this.closewerte.size()-1; z++){
                    muster.add(closewerte.get(z));
                }
                //List<Integer> muster = new ArrayList<>(this.closewerte.subList(this.closewerte.size()-(vergleichslaengen[j]+1),this.closewerte.size()-1));
                listRechner.add(new RechnerZusammenfasser(historie, muster, vergleichslaengen[j], auswertungslaenge, zusammenfasserInterval[j], spread, "EUR/USD", true, false));
                listThread.add(new Thread(listRechner.get((i*vergleichslaengen.length)+j)));
                listThread.get((i*vergleichslaengen.length)+j).start();
            }
        }

        for (int i = 0; i < threadPaare * vergleichslaengen.length; i++){
            try {
                listThread.get(i).join();
                System.out.println("Thread "+ i +": Formation "+listRechner.get(i).getTradeTmp().getAnzFormFound()+" mal gefunden");
                addiere(listRechner.get(i).getTradeTmp());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        tradeMessage = new TradeMessage(now, "EUR/USD", auswertungslaenge, anzFormFound, GewinnzaehlerLong, mittlererLongGewinn, hoherLongGewinn, sehrHoherLongGewinn, VerlustzaehlerLong, hoherLongVerlust, geringerShortGewinn, mittlererShortGewinn, hoherShortGewinn, sehrHoherShortGewinn, VerlustzaehlerShort, hoherShortVerlust);

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

    public int getGewinnzaehlerLong() {
        return GewinnzaehlerLong;
    }

    public void setGewinnzaehlerLong(int gewinnzaehlerLong) {
        GewinnzaehlerLong = gewinnzaehlerLong;
    }

    public int getVerlustzaehlerLong() {
        return VerlustzaehlerLong;
    }

    public void setVerlustzaehlerLong(int verlustzaehlerLong) {
        VerlustzaehlerLong = verlustzaehlerLong;
    }

    public int getGewinnzaehlerShort() {
        return GewinnzaehlerShort;
    }

    public void setGewinnzaehlerShort(int gewinnzaehlerShort) {
        GewinnzaehlerShort = gewinnzaehlerShort;
    }

    public int getVerlustzaehlerShort() {
        return VerlustzaehlerShort;
    }

    public void setVerlustzaehlerShort(int verlustzaehlerShort) {
        VerlustzaehlerShort = verlustzaehlerShort;
    }

    public int getGenerellPlus() {
        return GenerellPlus;
    }

    public void setGenerellPlus(int generellPlus) {
        GenerellPlus = generellPlus;
    }

    public int getGenerellMinus() {
        return GenerellMinus;
    }

    public void setGenerellMinus(int generellMinus) {
        GenerellMinus = generellMinus;
    }

    public int getHohesMinus() {
        return hohesMinus;
    }

    public void setHohesMinus(int hohesMinus) {
        this.hohesMinus = hohesMinus;
    }

    public int getHohesPlus() {
        return hohesPlus;
    }

    public void setHohesPlus(int hohesPlus) {
        this.hohesPlus = hohesPlus;
    }

    public int getHoherLongVerlust() {
        return hoherLongVerlust;
    }

    public void setHoherLongVerlust(int hoherLongVerlust) {
        this.hoherLongVerlust = hoherLongVerlust;
    }

    public int getGeringerLongGewinn() {
        return geringerLongGewinn;
    }

    public void setGeringerLongGewinn(int geringerLongGewinn) {
        this.geringerLongGewinn = geringerLongGewinn;
    }

    public int getMittlererLongGewinn() {
        return mittlererLongGewinn;
    }

    public void setMittlererLongGewinn(int mittlererLongGewinn) {
        this.mittlererLongGewinn = mittlererLongGewinn;
    }

    public int getHoherLongGewinn() {
        return hoherLongGewinn;
    }

    public void setHoherLongGewinn(int hoherLongGewinn) {
        this.hoherLongGewinn = hoherLongGewinn;
    }

    public int getSehrHoherLongGewinn() {
        return sehrHoherLongGewinn;
    }

    public void setSehrHoherLongGewinn(int sehrHoherLongGewinn) {
        this.sehrHoherLongGewinn = sehrHoherLongGewinn;
    }

    public int getGeringerShortGewinn() {
        return geringerShortGewinn;
    }

    public void setGeringerShortGewinn(int geringerShortGewinn) {
        this.geringerShortGewinn = geringerShortGewinn;
    }

    public int getMittlererShortGewinn() {
        return mittlererShortGewinn;
    }

    public void setMittlererShortGewinn(int mittlererShortGewinn) {
        this.mittlererShortGewinn = mittlererShortGewinn;
    }

    public int getHoherShortGewinn() {
        return hoherShortGewinn;
    }

    public void setHoherShortGewinn(int hoherShortGewinn) {
        this.hoherShortGewinn = hoherShortGewinn;
    }

    public int getSehrHoherShortGewinn() {
        return sehrHoherShortGewinn;
    }

    public void setSehrHoherShortGewinn(int sehrHoherShortGewinn) {
        this.sehrHoherShortGewinn = sehrHoherShortGewinn;
    }

    public int getHoherShortVerlust() {
        return hoherShortVerlust;
    }

    public void setHoherShortVerlust(int hoherShortVerlust) {
        this.hoherShortVerlust = hoherShortVerlust;
    }

    public int getAnzFormFound() {
        return anzFormFound;
    }

    public void setAnzFormFound(int anzFormFound) {
        this.anzFormFound = anzFormFound;
    }

    public TradeMessage getTradeMessage() {
        return tradeMessage;
    }

    public void setTradeMessage(TradeMessage tradeMessage) {
        this.tradeMessage = tradeMessage;
    }
}
