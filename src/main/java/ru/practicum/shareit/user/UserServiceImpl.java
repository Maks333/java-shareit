package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public User findById(long userId) {
        //User must be present
        return repository.findById(userId);
    }

    @Override
    public User create(User user) {
        //Name must be present
        //Email must be present
        //Check if Email is unique
        //Email should follow [a-zA-z]+@[a-zA-z]+[.com|.ru] pattern
        return repository.create(user);
    }

    @Override
    public User update(long userId, User user) {
        //User must be present
        //Name can be omitted
        //Email can be omitted
        //Email is unique
        //Email should follow [a-zA-z]+@[a-zA-z]+[.com|.ru] pattern
        return repository.update(userId, user);
    }

    @Override
    public void delete(long userId) {
        //User must be present
        repository.delete(userId);
    }
}
