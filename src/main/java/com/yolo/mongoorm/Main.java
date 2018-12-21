package com.yolo.mongoorm;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.yolo.mongoorm.Annotation.DatabaseField;
import com.yolo.mongoorm.Annotation.Entity;
import com.yolo.mongoorm.Annotation.Repository;
import com.yolo.mongoorm.Repository.MongoRepository;
import com.yolo.mongoorm.Repository.UserRepository;
import org.bson.Document;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;


public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> object = Class.forName("com.yolo.mongoorm.User");
        User user = (User) object.newInstance();
        Field[] fields = user.getClass().getDeclaredFields();

        String colName = object.getAnnotation(Entity.class).collection();

        for(Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation a : annotations) {
                if(a instanceof DatabaseField) {
                    DatabaseField databaseField = (DatabaseField) a;
                    if(field.getName() == "name") {
                        field.setAccessible(true);
                        field.set(user, "yolo");
                    }
                }
            }
        }

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("alevel");
        MongoCollection collection = database.getCollection(colName);


        Class<?> interf = Class.forName("com.yolo.mongoorm.Repository.UserRepository");
        Annotation[] annotations = interf.getDeclaredAnnotations();
        RepositorySingletone repositorySingletone = RepositorySingletone.getInstance();
        for(Annotation a : annotations) {
            if (a instanceof Repository) {
                repositorySingletone.addRepository(new UserRepository() {
                    @Override
                    public List<User> findAll() {
                        List<User> users = new ArrayList<>();
                        MongoCursor<Document> cursor = collection.find().iterator();
                        while(cursor.hasNext()) {
                            users.add(new User((String) cursor.next().get("name")));
                        }
                        return users;
                    }

                    @Override
                    public User insertOne(User user) {
                        collection.insertOne(user.toDocument());
                        return user;
                    }
                });
            }
        }
        repositorySingletone.getRepository("UserRepository").insertOne(user);
        System.out.println(repositorySingletone.getRepository("UserRepository").findAll());
    }
}
