package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.markers.OnUserCreate;
import ru.practicum.shareit.user.markers.OnUserUpdate;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserDto findById(long userId) {
        return UserMapper.toUserDto(repository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with " + userId + " is not found")));
    }

    @Validated(OnUserCreate.class)
    @Override
    public UserDto create(@Valid UserDto userDto) {
        User user = UserMapper.fromUserDto(userDto);
        return UserMapper.toUserDto(repository.create(user));
    }

    @Validated(OnUserUpdate.class)
    @Override
    public UserDto update(long userId, @Valid UserDto userDto) {
        User user = UserMapper.fromUserDto(userDto);
        return UserMapper.toUserDto(repository.update(userId, user));
    }

    @Override
    public void delete(long userId) {
        repository.delete(userId);
    }
}
