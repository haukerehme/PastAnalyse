package de.hrs.dao;

import de.hrs.model.TradeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Date;

/**
 * Created by hrs on 10.07.16.
 */
@Repository
public class TradeMessageDao {
    public static Logger log = LoggerFactory.getLogger(TradeMessageDao.class);

    private JdbcTemplate template;

    @Autowired
    private void setDataSource(DataSource dataSource){
        DataSource dataSource1 = dataSource;
        this.template = new JdbcTemplate(dataSource);
    }

    private static final String insertSql =
            "Replace INTO TradeMessageTest ( " +
                    "timestamp, " +
                    "instrument, " +
                    "timeperiod, " +
                    "anzFound, " +
                    "longWin, " +
                    "longWinMiddle, " +
                    "longWinHigh, " +
                    "longWinVeryHigh, " +
                    "longLose, " +
                    "longLoseHigh, " +
                    "shortWin, " +
                    "shortWinMiddle, " +
                    "shortWinHigh, " +
                    "shortWinVeryHigh, " +
                    "shortLose, " +
                    "shortLoseHigh, " +
                    "insertedDate) " + "VALUES (" +
                    "?, ?, ?, ?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "? ) ";

    public void persist(TradeMessage tradeMessage){
        Object[] params = new Object[] {
                tradeMessage.getTimestamp(),
                tradeMessage.getInstrument(),
                tradeMessage.getTimeperiod(),
                tradeMessage.getAnzFound(),
                tradeMessage.getLongWin(),
                tradeMessage.getLongWinMiddle(),
                tradeMessage.getLongWinHigh(),
                tradeMessage.getLongWinVeryHigh(),
                tradeMessage.getLongLose(),
                tradeMessage.getLongLoseHigh(),
                tradeMessage.getShortWin(),
                tradeMessage.getShortWinMiddle(),
                tradeMessage.getShortWinHigh(),
                tradeMessage.getShortWinVeryHigh(),
                tradeMessage.getShortLose(),
                tradeMessage.getShortLoseHigh(),
                new Date()
        };
        int[] types = new int[] {
                Types.TIMESTAMP,
                Types.VARCHAR,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.DATE
        };
        int row = template.update(insertSql, params, types);
        log.info("Insert row for Dat{}", tradeMessage.getTimestamp());
    }

}
