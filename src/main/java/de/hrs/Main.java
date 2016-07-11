package de.hrs;

import de.hrs.controller.Analyse;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by hrs on 18.06.16.
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws ParseException {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc-config.xml");
        if (args.length == 2){


        }else if (args.length == 1){
            //2015-01-01 18:29:00
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Timestamp timestamp = new Timestamp(dateFormat.parse(args[0]).getTime());
            context.getBean(Analyse.class).startAnalyse(timestamp);
        }else{
            System.out.println("Bitte geben Sie die einen oder zwei Parameter an");
        }
    }
}