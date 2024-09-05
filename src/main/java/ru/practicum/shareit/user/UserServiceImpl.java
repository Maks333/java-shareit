package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.markers.onUserCreate;
import ru.practicum.shareit.user.markers.onUserUpdate;

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


    @Validated(onUserCreate.class)
    @Override
    public User create(@Valid User user) {
        return repository.create(user);
    }

    @Validated(onUserUpdate.class)
    @Override
    public User update(long userId, @Valid User user) {
        return repository.update(userId, user);
    }

    @Override
    public void delete(long userId) {
        repository.delete(userId);
    }
}
