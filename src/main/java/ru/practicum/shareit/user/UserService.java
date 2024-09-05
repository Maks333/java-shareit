package ru.practicum.shareit.user;

import jakarta.validation.Valid;

public interface UserService {
    User findById(long userId);

    User create(@Valid User user);

    User update(long userId, User user);

    void delete(long userId);
}
