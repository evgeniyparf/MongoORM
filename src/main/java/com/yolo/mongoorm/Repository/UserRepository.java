package com.yolo.mongoorm.Repository;

import com.yolo.mongoorm.Annotation.Repository;
import com.yolo.mongoorm.User;

@Repository
public interface UserRepository extends MongoRepository<User> {

}
