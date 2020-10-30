package org.openjfx;

import org.control.CalculationsDatabase;
import org.control.CrudActions;
import org.control.EmployeeActions;

public class ControllerJFX{

    EmployeeActions employeeActions = new EmployeeActions();
    CalculationsDatabase calculationsDatabase = new CalculationsDatabase();
    CrudActions crudActions = new CrudActions();
    App app = new  App();

//    public void clickedButton(int id, String text) {
//        System.out.println("clickedButton");
//        switch (id) {
//            case 1:
//                this.pressClockInButton(text);
//                break;
//            case 2 :
//                this.pressClockOutButton(text);
//                break;
//        }
//    }

    public int pressClockInButton(String text) {
        int inputID = Integer.parseInt(text);
        if (crudActions.readAField(inputID, "_id") == null) {
            // password is incorrect
            return 1;
        } else {
            if (crudActions.compareTimeBeforeAfter(inputID) == 0 || crudActions.compareTimeBeforeAfter(inputID) == 2) {
                employeeActions.logIn(inputID);
                // success
                return 2;
            } else {
                // have not Clocked Out
                return 0;
            }
        }
    }

    public int pressClockOutButton(String text) {
        int inputID = Integer.parseInt(text);
        if (crudActions.readAField(inputID, "_id") == null) {
            // password is incorrect
            return 1;
        } else {
            if (crudActions.compareTimeBeforeAfter(inputID) == 1 || crudActions.compareTimeBeforeAfter(inputID) == 2) {
                employeeActions.logOut(inputID);
                calculationsDatabase.addTheHoursWorked(inputID, crudActions.readAField(inputID, "lastName"));
                // success
                return 2;
            } else {
                // have not Clocked Out
                return 0;
            }
        }
    }
}
