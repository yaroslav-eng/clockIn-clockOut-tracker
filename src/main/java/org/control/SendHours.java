package org.control;

import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SendHours {

    CrudActions crudActions = new CrudActions();
    CalculationsDatabase calculationsDatabase = new CalculationsDatabase();

    // calculates the total hours worked in the database and updates the field totalHours;
    public void calcTotalHoursForWorker() {
        for (ObjectId objectId : crudActions.getIds()){
            crudActions.updateAFieldDataTwoById("_id", objectId, "totalHours", calculationsDatabase.getTheHoursWorked(crudActions.getSubfieldsOfFieldInListStringParam(objectId)));

        }
    }

    public void creatingFile() {
            try {
                File myObj = new File("filename.txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
    }

    // sends for all the employees
    public void writingToFile() {
        try {
            FileWriter myWriter = new FileWriter("filename.txt");

            for (ObjectId objectId : crudActions.getIds()) {
                myWriter.write("Last Name: " + crudActions.helperMethodForGettingJustTheFieldOfFieldNoDocuent(objectId, "lastName"));
//                myWriter.write(""+crudActions.convertDocumentToReadFormat(objectId, "lastName.lastName"));
                myWriter.write("\n");
                myWriter.write("First Name: " + crudActions.convertDocumentToReadFormat(objectId, "firstName"));
                myWriter.write("\n");
                myWriter.write("Total Hours Worked in 2 weeks is: "+crudActions.convertDocumentToReadFormat(objectId, "totalHours"));
                myWriter.write("\n \n");
//                crudActions.deleteAField("_id", objectId, "data");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        SendHours sendHours = new SendHours();
//        sendHours.calcTotalHoursForWorker();
//        sendHours.writingToFile();
//    }
}
