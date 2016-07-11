package de.hrs.controller;

import de.hrs.Rechner.AnalyseMehereVergleichsstrecken;
import de.hrs.dao.EurUsdDao;
import de.hrs.model.Eurusd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by hrs on 10.07.16.
 */
@Component
public class Analyse {

    @Resource
    EurUsdDao eurUsdDao;

    public static Logger log = LoggerFactory.getLogger(Analyse.class);


    public void startAnalyse(Timestamp von){
        Timestamp bis = eurUsdDao.findLastEntry().getZeit();
        startAnalyseVonBis(von, bis);
    }

    public void startAnalyseVonBis(Timestamp von, Timestamp bis){
        List<Integer> eurusdListPast = eurUsdDao.findAllDifferences(eurUsdDao.findFirstEntry().getZeit(),von);
        List<Integer> eurusdListFuture = eurUsdDao.findAllDifferences(von, bis);
        analyse(eurusdListPast, eurusdListFuture);
    }

    private void analyse(List<Integer> eurusdListPast, List<Integer> eurusdListFuture){
        int auswertungslaenge = 20;
        AnalyseMehereVergleichsstrecken analyse = new AnalyseMehereVergleichsstrecken();
        for(int newValue : eurusdListFuture.subList(0,eurusdListFuture.size()-(auswertungslaenge+1))) {
//            new AnalyseMehereVergleichsstrecken(
//                    new Timestamp(new Date().getTime()),
//                    eurusdListPast, eurusdListPast.size() - 1, null, auswertungslaenge, 1, "EUR/USD").run();
            //ToDo: Change the ArrayList eurusdListFuture to (Integer,Timestamp) to set Now in AnalyseMehereVergleichsstrecken

//            analyse.setNow();
            eurusdListPast.add(newValue);
        }
    }

}
