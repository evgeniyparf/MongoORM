package com.yolo.mongoorm;

import com.yolo.mongoorm.Annotation.DatabaseField;
import com.yolo.mongoorm.Annotation.Entity;
import org.bson.Document;
import org.bson.types.ObjectId;

@Entity(collection = "users")
public class User {

    @DatabaseField(name = "ObjectId")
    private ObjectId objectId;

    @DatabaseField(name = "name")
    private String name;

    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "objectId=" + objectId +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * FOR TEST
     */

    public Document toDocument() {
        return new Document().append("name", name);
    }
}
