package de.hrs.dao;

import de.hrs.model.Eurusd;
import de.hrs.model.EurusdDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by hrs on 18.06.16.
 */
@Repository
public class EurUsdDao {
    private JdbcTemplate template;

    @Autowired
    private void setDataSource(DataSource dataSource) {
        DataSource dataSource1 = dataSource;
        this.template = new JdbcTemplate(dataSource);
    }

    public Eurusd findLastEntry() {
        String sql = "Select * from eurusdTest where zeit = (Select max(zeit) from eurusdTest)";

        List<Eurusd> eurusdList = new ArrayList<Eurusd>();

        List<Map<String, Object>> rows = template.queryForList(sql);

        for (Map row : template.queryForList(sql)) {
            eurusdList.add(new Eurusd((Timestamp) row.get("zeit"), (Double) row.get("wert")));
        }
        return eurusdList.get(0);
    }

    public Eurusd findFirstEntry() {
        String sql = "Select * from eurusdTest where zeit = (Select min(zeit) from eurusdTest)";

        List<Eurusd> eurusdList = new ArrayList<Eurusd>();

        List<Map<String, Object>> rows = template.queryForList(sql);

        for (Map row : template.queryForList(sql)) {
            eurusdList.add(new Eurusd((Timestamp) row.get("zeit"), (Double) row.get("wert")));
        }
        return eurusdList.get(0);
    }

    public List<Integer> findAllDifferences(Timestamp von, Timestamp bis) {
        String sql = "SELECT * FROM eurusdTest where zeit > ? AND zeit < ? order by zeit ASC";

        List<Integer> eurusdList = new ArrayList<Integer>();
        double wertNeu;
        int diff;
        Object[] params = new Object[] { von, bis};
        int[] types = new int[] { Types.TIMESTAMP, Types.TIMESTAMP};

        List<Map<String, Object>> rows = template.queryForList(sql,params,types);

        double wertAlt = (Double) rows.get(0).get("wert");

        for (Map row : rows) {
            wertNeu = (Double) row.get("wert");
            diff = (int) (10000 * wertNeu - 10000 * wertAlt);
            eurusdList.add(diff);
            wertAlt = wertNeu;
        }
        return eurusdList;
    }

    public List<EurusdDiff> findAllDifferencesWithTimestamp(Timestamp von, Timestamp bis) {
        String sql = "SELECT *, (\n" +
                "DAYNAME( DATE( zeit ) ) =  \"sunday\"\n" +
                "OR DAYNAME( DATE( zeit ) ) =  \"saturday\"\n" +
                "OR TIME( zeit ) > '21:00:00'\n" +
                "OR TIME( zeit ) < '07:00:00'\n" +
                ") AS pause" +
                " FROM eurusdTest where zeit > ? AND zeit < ? order by zeit ASC";

        List<EurusdDiff> eurusdList = new ArrayList<EurusdDiff>();
        double wertNeu;
        int diff;
        Object[] params = new Object[] { von, bis};
        int[] types = new int[] { Types.TIMESTAMP, Types.TIMESTAMP};

        List<Map<String, Object>> rows = template.queryForList(sql,params,types);

        double wertAlt = (Double) rows.get(0).get("wert");
        boolean tradeable;
        int i = 0;

        for (Map row : rows) {
            wertNeu = (Double) row.get("wert");
            diff = (int) (10000 * wertNeu - 10000 * wertAlt);

            if((Long) rows.get(i).get("pause") > 0){
                tradeable = false;
            }else{
                tradeable = true;
            }
            eurusdList.add(new EurusdDiff(diff, (Timestamp) rows.get(i).get("zeit"), tradeable));
            wertAlt = wertNeu;
            i++;
        }
        return eurusdList;
    }
}
