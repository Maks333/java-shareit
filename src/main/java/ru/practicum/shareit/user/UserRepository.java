package ru.practicum.shareit.user;

public interface UserRepository {
    User findById(long userId);

    User create(User user);

    User update(long userId, User user);

    void delete(long userId);
}
