package org.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CalcTime {
    public Calendar cal = Calendar.getInstance();
    public SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public Date date;
//    private ArrayList<String> prob = new ArrayList<String>();


    public int getDayOfTheMonth() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

//    public String getTime() {
////        System.out.println(date+"This is DATE");
////        System.out.println(formatter.format(date));
//        System.out.println(cal.getTime());
//        return formatter.format(date);
//    }


    public String getDateTime() {
        date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

//    public void addStartTime() {
//        prob.add(getTime());
//    }

    public String timeCalc (String dateStart, String dateStop) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        LocalDateTime dateTime1= LocalDateTime.parse(dateStart, formatter);
        LocalDateTime dateTime2= LocalDateTime.parse(dateStop, formatter);

        long difffInHour = Duration.between(dateTime1, dateTime2).toHours();
        long diffInSeconds = Duration.between(dateTime1, dateTime2).toSeconds()%60;
        long diffInMinutes = Duration.between(dateTime1, dateTime2).toMinutes() %60;

        String TimeTaken = String.format("%s hours : %s mins : %s sec",
//            String TimeTaken = String.format("%s:%s:%s",
                difffInHour,
                diffInMinutes,
                diffInSeconds);
        return TimeTaken;

    }

    public int timeComparison (String dateOne, String dateTwo) {
        Date date1 = null;
        Date date2 = null;
        try {
             date1 = formatter.parse(dateOne);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
             date2 = formatter.parse(dateTwo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1.compareTo(date2) > 0) {
//            System.out.println("Date1 is after Date2");
            return 1;
        } else if (date1.compareTo(date2) < 0) {
//            System.out.println("Date1 is before Date2");
            return 0;
        } else if (date1.compareTo(date2) == 0) {
//            System.out.println("Date1 is equal to Date2");
            return 2;
        } else {
            System.out.println("How to get here?");
            return -1;
        }
    }




}
