package reflect;

import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/21 11:16
 */
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }



    public static void test(){
        LocalDate fromDate = toLocalDate(new Date());
        LocalDate toDate = toLocalDate(DateUtils.addHours(new Date(), 3));
        System.out.println(fromDate);
        System.out.println(toDate);
        long betweenDays = ChronoUnit.DAYS.between(toDate, fromDate);
        System.out.println(betweenDays);
    }
    public static LocalDate toLocalDate (Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
