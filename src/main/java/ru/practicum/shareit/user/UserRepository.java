package ru.practicum.shareit.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long userId);

    User create(User user);

    User update(long userId, User user);

    void delete(long userId);
}
