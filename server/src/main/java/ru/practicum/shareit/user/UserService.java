package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto findById(long userId);

    UserDto create(UserDto user);

    UserDto update(long userId, UserDto user);

    void delete(long userId);
}
