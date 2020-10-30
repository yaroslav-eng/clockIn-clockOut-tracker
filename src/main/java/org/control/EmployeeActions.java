package org.control;

public class EmployeeActions extends CalcTime implements ConnectionMongo, EmployeeActInterface {

    public EmployeeActions(){

        super();
    }

    CrudActions crudActions = new CrudActions();

    @Override
    public long logIn(int pass) {
        return crudActions.updateAField("password", pass, "logIn.0", getDateTime());
    }

    @Override
    public long logOut(int pass) {
        return crudActions.updateAField("password", pass, "logIn.1", getDateTime());
    }
}