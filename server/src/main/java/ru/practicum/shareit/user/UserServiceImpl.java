package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserDto findById(long userId) {
        return UserMapper.toUserDto(repository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with " + userId + " is not found")));
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.fromUserDto(userDto);
        return UserMapper.toUserDto(repository.save(user));
    }

    @Override
    public UserDto update(long userId,  UserDto userDto) {
        User user = repository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with " + userId + " is not found"));

        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) user.setEmail(userDto.getEmail());
        if (userDto.getName() != null && !userDto.getName().isBlank()) user.setName(userDto.getName());

        return UserMapper.toUserDto(repository.save(user));
    }

    @Override
    public void delete(long userId) {
        repository.deleteById(userId);
    }
}
