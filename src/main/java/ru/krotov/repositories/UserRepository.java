package ru.krotov.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.krotov.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
