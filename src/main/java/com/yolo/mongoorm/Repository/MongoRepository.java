package com.yolo.mongoorm.Repository;

import java.util.List;

public interface MongoRepository<T> {
    public List<T> findAll();
    public T insertOne(T t);
}
