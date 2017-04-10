package ru.krotov.service;

import ru.krotov.models.User;

import java.util.List;

public interface UserService {
    void addRandomUser();
    List<User> getAll();
}
