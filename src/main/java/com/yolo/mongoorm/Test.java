package com.yolo.mongoorm;

import java.util.List;

public class Test {

    private static RepositorySingletone repositorySingletone = RepositorySingletone.getInstance();

    public static List<User> getUsers() {
        return repositorySingletone.getRepository("UserRepository").findAll();
    }
}
