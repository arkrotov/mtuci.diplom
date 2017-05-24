package services;

import models.User;

import java.util.List;

public interface UserService {
    void addRandomUser();
    List<User> getAll();
}
