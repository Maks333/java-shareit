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
    public User findById(long userId) {
        return repository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with " + userId + " is not found"));
    }

    @Validated(OnUserCreate.class)
    @Override
    public UserDto create(@Valid UserDto userDto) {
        User user = UserMapper.fromUserDto(userDto);
        return UserMapper.toUserDto(repository.create(user));
    }

    @Validated(OnUserUpdate.class)
    @Override
    public User update(long userId, @Valid User user) {
        return repository.update(userId, user);
    }

    @Override
    public void delete(long userId) {
        repository.delete(userId);
    }
}
