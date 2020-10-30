package org.control;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public interface ConnectionMongo {
    String databaseName = "your database name";
    String uri = "database URL";
    MongoClient mongoClient= MongoClients.create(uri);
    MongoDatabase db = mongoClient.getDatabase("your database name");
    MongoCollection<Document> collectionTest = db.getCollection("your database name you want to operate");
    MongoCollection<Document> collectionHistory = db.getCollection("your database name you want to operate");

}
