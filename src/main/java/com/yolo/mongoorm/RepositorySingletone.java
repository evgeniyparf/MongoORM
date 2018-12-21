package com.yolo.mongoorm;

import com.yolo.mongoorm.Repository.MongoRepository;

import java.util.HashMap;

public class RepositorySingletone {

    private static RepositorySingletone repositorySingletone;
    public HashMap<String, MongoRepository> repositories = new HashMap<>();

    public static RepositorySingletone getInstance() {
        if(repositorySingletone == null)
            repositorySingletone = new RepositorySingletone();
        return repositorySingletone;
    }

    public void addRepository(MongoRepository repository) {
        repositories.put(repository.getClass().getInterfaces()[0].getSimpleName(), repository);
    }

    public MongoRepository getRepository(String name) {
        return repositories.get(name);
    }
}
