package org.control;

import com.mongodb.BasicDBObject;
import com.mongodb.DBRef;
import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class CrudActions implements CrudMethods, ConnectionMongo{



    private List<String> hours = new ArrayList<>();
    private List<String> shifts = new ArrayList<>();

    private final CalcTime calc_time = new CalcTime();

    private String clockIn = "";
    private String clockOut = "";
    private final String totalHours = "";

    BasicDBObject whereQuery = new BasicDBObject();

    @Override
    public void createEmployee(EmployeeInstance employee) {

        Document canvas = new Document("firstName", employee.getFirstName())
                .append("lastName", employee.getLastName())
                .append("position", employee.getPosition())
                .append("username", employee.getUsername())
                .append("password", employee.getPassword())
                .append("logIn", hours);
        collectionTest.insertOne(canvas);

        this.createSecondaryDatabase();
    }

    // read 1 field by an integer and project 1 field
    @Override
    public Document readAField(int pass, String projectAField) {
        //test database
        Document nodes = collectionTest
                .find(new BasicDBObject("password", pass))
                .projection(Projections.fields(Projections.include(projectAField), Projections.excludeId()))
                .first();
//        System.out.println(nodes+ "Node");
        return nodes;
    }

    @Override
    public Document readAFieldDatabaseTwo(int pass, String projectAField) {
        //test database
        Document nodes = collectionHistory
                .find(new BasicDBObject("password", pass))
                .projection(Projections.fields(Projections.include(projectAField), Projections.excludeId()))
                .first();
        return nodes;
    }

    @Override
    public Document getFieldByObjectIdNumber(ObjectId idToSearchField, String projectAField) {
        Document document = collectionTest
                .find(new BasicDBObject("_id", idToSearchField))
                .projection(Projections.fields(Projections.include(projectAField), Projections.excludeId())).first();
        return document;
    }

    @Override
    public Document getFieldByObjectIdNumberData2(Object idToSearchField, String projectAField) {
        Document document = collectionHistory
                .find(new BasicDBObject("_id", idToSearchField))
                .projection(Projections.fields(Projections.include(projectAField), Projections.excludeId())).first();
        return document;
    }

    @Override
    public long updateAField(String fieldName, int value, String setToFieldName, String setToValue) {
        UpdateResult updateResult = collectionTest.updateOne(eq(fieldName, value), set(setToFieldName, setToValue));
        return updateResult.getModifiedCount();
    }

    @Override
    public long updateAFieldDataTwo(String fieldName, int value, String setToFieldName, String setToValue) {
        UpdateResult updateResult = collectionHistory.updateOne(eq(fieldName, value), set(setToFieldName, setToValue));
        return updateResult.getModifiedCount();
    }

    public long updateAFieldDataTwoById(String fieldName, ObjectId id, String setToFieldName, String setToValue) {
        UpdateResult updateResult = collectionHistory.updateOne(eq(fieldName, id), set(setToFieldName, setToValue));
        return updateResult.getModifiedCount();
    }

    // deleted the Array of Hours worked in History database
    @Override
    public long deleteAField(String fieldName, ObjectId id, String setToFieldName) {
        hours = new ArrayList<>();
        UpdateResult updateResult = collectionHistory.updateOne(eq(fieldName, id), set(setToFieldName, hours));
        return updateResult.getModifiedCount();
    }

    // gets called on insert
    public void createSecondaryDatabase() {
        for (ObjectId y : getIds()) {
            DBRef refer = new DBRef(databaseName, y);
            Document canvas = new Document()
                    .append("_id", refer.getId())
                    .append("lastName", getFieldByObjectIdNumber(y, "lastName"))
                    .append("firstName", getFieldByObjectIdNumber(y, "firstName"))
                    .append("data", shifts)
                    .append("totalHours", totalHours);
            collectionHistory.insertOne(canvas);
        }
    }

    public List<ObjectId> getIds(){
        String id = "_id";
        List<ObjectId> hours = new ArrayList<>();
        FindIterable<Document> collection = collectionTest.find();
        for (Document z : collection){
            hours.add((ObjectId) z.get(id));
        }
        return hours;
    }

    // history database return List Object
    public List<Object> getSubfieldsOfField(){
        List<Object> hoursList = new ArrayList<>();
        List<Document> list;
        // loop through ids
        for (ObjectId y: getIds()){
            List<Object> prob = new ArrayList<>();
            //getting the doc->data->doc,hoursWorked

            // History Database
            Document nodes = getFieldByObjectIdNumberData2(y, "data.hoursWorked");
            //gets doc->hoursWorked
//            System.out.println(nodes.get("data") + "GET DATA");
            list = (List<Document>) nodes.get("data");
            for(Document d:list){
                prob.add(d.get("hoursWorked"));
            }
            hoursList.add(prob);
        }

        return hoursList;
    }

    // To get a Field itself and NO Document or an object
    public List<String> getSubfieldsOfFieldInListStringParam(ObjectId id){
        List<Document> list;
        List<String> strings = new ArrayList<>();
        // loop through ids

        // History Database
        Document nodes = getFieldByObjectIdNumberData2(id, "data.hoursWorked");
        //gets doc->hoursWorked
        list = (List<Document>) nodes.get("data");
        for(Document d:list){
            strings.add((String) d.get("hoursWorked"));
        }

//        System.out.println(strings + "this is HOURS");
        return strings;
    }

    // for Sub Field of A field. Gets a that value of a sub field in a nice format.
    public String helperMethodForGettingJustTheFieldOfFieldNoDocuent(ObjectId id, String fieldToGetForUI){
        Document document = getFieldByObjectIdNumberData2(id, fieldToGetForUI);
        document = (Document) document.get(fieldToGetForUI);
        return document.getString(fieldToGetForUI);
    }

    // retrieves just the field itself and not the object. Good for UI
    // converts a doc to a read format
    // gets the UI readable format of the objectID and the field to project for the secondDatabase
    public String convertDocumentToReadFormat(ObjectId id, String itemToGetforUI){
        Document nodes = getFieldByObjectIdNumberData2(id, itemToGetforUI);
        return nodes.getString(itemToGetforUI);
    }


    public String minutesWorked(int pass){
        whereQuery = new BasicDBObject();
        whereQuery.put("password", pass);
        MongoCursor<Document> cursor = collectionTest.find(whereQuery).iterator();
        try {
            while (cursor.hasNext()) {
                Document str = cursor.next();
                List<String> list = (List<String>)str.get("logIn");
                this.clockIn = list.get(0);
                this.clockOut = list.get(1);
            }
        } finally {
            cursor.close();
        }
        return calc_time.timeCalc(clockIn,clockOut);
    }

    public int compareTimeBeforeAfter(int pass){
        whereQuery = new BasicDBObject();
        whereQuery.put("password", pass);
        MongoCursor<Document> cursor = collectionTest.find(whereQuery).iterator();
        try {
            while (cursor.hasNext()) {
                Document str = cursor.next();
                List<String> list = (List<String>)str.get("logIn");
                this.clockIn = list.get(0);
                this.clockOut = list.get(1);
//                System.out.println(this.s + "SSSSS");
            }
        } finally {
            cursor.close();
        }
        return calc_time.timeComparison(clockIn,clockOut);
    }
}
