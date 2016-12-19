package de.hrs;

import de.hrs.controller.Analyse;
import de.hrs.repair.FixDb;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by hrs on 18.06.16.
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws ParseException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc-config.xml");

//        if (args.length == 1 && args[0].equals("fix")){
                context.getBean(FixDb.class).addDbFieldsCurrentValueAndFutureValue();
        /*}else {

            if (args.length == 2) {


            } else if (args.length == 1) {
                //2015-01-01 18:29:00
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Timestamp timestamp = new Timestamp(dateFormat.parse(args[0]).getTime());
                context.getBean(Analyse.class).startAnalyse(timestamp);
            } else {

                GregorianCalendar gc = new GregorianCalendar();
                int year = randBetween(2009, 2015);
                gc.set(gc.YEAR, year);
                int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
                gc.set(gc.DAY_OF_YEAR, dayOfYear);

                context.getBean(Analyse.class).startAnalyse(new Timestamp(gc.getTime().getTime()));

//            System.out.println(gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH));
//
//            System.out.println("Bitte geben Sie einen oder zwei Parameter an");
            }
        }*/
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}