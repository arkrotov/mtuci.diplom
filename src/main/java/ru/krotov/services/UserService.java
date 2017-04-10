package ru.krotov.services;

import ru.krotov.models.User;

import java.util.List;

public interface UserService {
    void addRandomUser();
    List<User> getAll();
}
