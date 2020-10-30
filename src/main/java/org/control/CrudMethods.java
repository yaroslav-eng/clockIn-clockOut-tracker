package org.control;

import org.bson.Document;
import org.bson.types.ObjectId;

public interface CrudMethods {

    public void createEmployee( EmployeeInstance employee);

    public Document readAField(int pass, String projectAField);

    public Document readAFieldDatabaseTwo(int pass, String projectAField);

    public Document getFieldByObjectIdNumber(ObjectId idToSearchField, String projectAField);

    public Document getFieldByObjectIdNumberData2(Object idToSearchField, String projectAField);

    public long updateAFieldDataTwo(String fieldName, int value, String setToFieldName, String setToValue);

    public long updateAField(String fieldName, int value, String setToFieldName, String setToValue);

    public long deleteAField(String fieldName, ObjectId id, String setToFieldName);

}
