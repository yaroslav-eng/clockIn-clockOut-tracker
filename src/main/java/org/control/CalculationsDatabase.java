package org.control;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;

public class CalculationsDatabase extends CrudActions {
    public CalculationsDatabase(){
        super();
    }

    private CalcTime calc_time = new CalcTime();
    private List<String> togetherHours = new ArrayList<>();
    private List<Object> hours = getSubfieldsOfField();

    List<String> strings;


public String getTheHoursWorked(List<String> hoursString) {

    // Start with a time of 0:0
    int sumHours = 0;
    int sumMinutes = 0;
    int sumSeconds = 0;

    // Iterate the list and add all hours, minutes and seconds separately after
    // parsing the time strings using the corresponding formatter
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H' hours : 'm' mins : 's' sec'");
    for (String strTime : hoursString) {
        LocalTime time = LocalTime.parse(strTime, timeFormatter);
        sumHours += time.getHour();
        sumMinutes += time.getMinute();
        sumSeconds += time.getSecond();
    }
//
    // Adjust hour, minutes and seconds if minute and/or second exceed 60
    sumMinutes += sumSeconds / 60;
    sumSeconds %= 60;
    sumHours += sumMinutes / 60;
    sumMinutes %= 60;

    String strSum = String.format("%d hours : %d mins : %d sec", sumHours, sumMinutes, sumSeconds);
    return strSum;
}

    public void addTheHoursWorked(int pass, Document obId) {
//        ObjectId od = new ObjectId(obId);
        String getFinalTime = minutesWorked(pass);
        BasicDBObject tenant = new BasicDBObject();
        tenant.put("day", calc_time.getDateTime());
        tenant.put("hoursWorked", getFinalTime);
        UpdateResult updateResult = collectionHistory.updateOne(eq("lastName", obId), push("data", tenant));
    }

    public String convertDocumentToReadFormat(Document doc, String itemToGetforUI){
        return doc.getString(itemToGetforUI);
    }

}
