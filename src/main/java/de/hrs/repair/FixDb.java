package de.hrs.repair;

import de.hrs.dao.EurUsdDao;
import de.hrs.dao.EurUsdRepository;
import de.hrs.dao.TradeMessageRepository;
import de.hrs.model.Eurusd;
import de.hrs.model.TradeMessage;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created by hrs on 18.12.16.
 */
//ToDo: Tabellennamen und Spaltennamen müssen evtl alle nur mit Kleinbuchstaben geschrieben werden

@Component
public class FixDb {
    @Resource
    EurUsdDao eurUsdDao;

    @Resource
    EurUsdRepository eurUsdRepository;

    @Resource
    TradeMessageRepository tradeMessageRepository;

    public void addDbFieldsCurrentValueAndFutureValue(){
        for (TradeMessage tradeMessage :tradeMessageRepository.findAll()) {
            //ToDo: Hole EurUsd vom gleichen Timestamp und EurUsd + 20min
            //ToDo: Beide sollen dann in das tradeMessage-Obj geschrieben werden und persistiert werden
                if (tradeMessage.getCurrentvalue() == 0 ){
                    Eurusd currentValue = eurUsdRepository.findOne(new Timestamp(tradeMessage.getTimestamp().getTime()));
                    if(currentValue != null) {
                        tradeMessage.setCurrentvalue(currentValue.getWert());
                    }
                }
                if (tradeMessage.getTwentyminfuturevalue() == 0 ) {
                    Eurusd futureEurusd = eurUsdRepository.findOne(new Timestamp(tradeMessage.getTimestamp().getTime() + (20 * 60)));
                    if(futureEurusd != null) {
                        tradeMessage.setTwentyminfuturevalue(futureEurusd.getWert());
                    }
                }
                tradeMessageRepository.save(tradeMessage);

        }
    }
}
