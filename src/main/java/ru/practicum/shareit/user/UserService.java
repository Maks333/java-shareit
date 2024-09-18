package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto findById(long userId);

    UserDto create(@Valid UserDto user);

    UserDto update(long userId, @Valid UserDto user);

    void delete(long userId);
}
