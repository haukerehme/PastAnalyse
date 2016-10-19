package de.hrs.controller;

import de.hrs.Rechner.AnalyseMehereVergleichsstrecken;
import de.hrs.dao.EurUsdDao;
import de.hrs.dao.TradeMessageDao;
import de.hrs.model.Eurusd;
import de.hrs.model.EurusdDiff;
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
    TradeMessageDao tradeMessageDao;

    @Resource
    EurUsdDao eurUsdDao;

    public static Logger log = LoggerFactory.getLogger(Analyse.class);


    public void startAnalyse(Timestamp von){
        Timestamp bis = eurUsdDao.findLastEntry().getZeit();
        startAnalyseVonBis(von, bis);
    }

    public void startAnalyseVonBis(Timestamp von, Timestamp bis){
        List<Integer> eurusdListPast = eurUsdDao.findAllDifferences(eurUsdDao.findFirstEntry().getZeit(),von);
        List<EurusdDiff> eurusdListFuture = eurUsdDao.findAllDifferencesWithTimestamp(von, bis);
        analyse(eurusdListPast, eurusdListFuture);
    }

    private void analyse(List<Integer> eurusdListPast, List<EurusdDiff> eurusdListFuture){
        int auswertungslaenge = 20;
        AnalyseMehereVergleichsstrecken analyse;
//        for(EurusdDiff newValue : eurusdListFuture.subList(0,eurusdListFuture.size()-(auswertungslaenge+1))) {
        for (int i = 0; i < eurusdListFuture.size()-(30+1); i++){
            //ToDo: Check if Weekend or night
            if (eurusdListFuture.get(i).isTradeable()) {
                log.info("Analysiere {}", eurusdListFuture.get(i).getTimestamp());
                analyse  = new AnalyseMehereVergleichsstrecken(eurusdListFuture.get(i).getTimestamp(),
                        eurusdListPast,eurusdListPast.size() - 1,
                        auswertungslaenge, 1, "EUR/USD"
                        );
                analyse.run();
                tradeMessageDao.persist(analyse.getTradeMessage());
            }else{
                log.info("Überspringe {}", eurusdListFuture.get(i).getTimestamp());
            }
            eurusdListPast.add(eurusdListFuture.get(i).getDiff());
        }
    }

}
