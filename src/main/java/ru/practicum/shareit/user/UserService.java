package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    User findById(long userId);

    UserDto create(@Valid UserDto user);

    User update(long userId, @Valid User user);

    void delete(long userId);
}
