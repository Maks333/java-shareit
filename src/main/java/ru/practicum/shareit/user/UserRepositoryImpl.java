package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.EmailIsNotUniqueException;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Optional<User> findById(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public User create(User user) {
        if (isEmailNotUnique(user.getEmail())) {
            throw new EmailIsNotUniqueException("Email must be unique");
        }

        long userId = nextUserId();
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public User update(long userId, User user) {
        User userToUpdate = users.get(userId);
        if (userToUpdate == null) {
            throw new NotFoundException("User with id " + userId + " is not found");
        }

        if (user.getName() != null) userToUpdate.setName(user.getName());
        if (user.getEmail() != null) {
            if (isEmailNotUnique(user.getEmail())) {
                throw new EmailIsNotUniqueException("Email must be unique");
            }
            userToUpdate.setEmail(user.getEmail());
        }
        users.put(userId, userToUpdate);
        return userToUpdate;
    }

    @Override
    public void delete(long userId) {
        if (users.get(userId) == null) {
            throw new NotFoundException("User with id " + userId + " is not found");
        }
        users.remove(userId);
    }

    private boolean isEmailNotUnique(String email) {
        return users.values()
                .stream()
                .map(User::getEmail)
                .anyMatch(e -> e.equals(email));
    }

    private long nextUserId() {
        long nextId = users.keySet()
                .stream()
                .max(Long::compareTo)
                .orElse(1L);
        return ++nextId;
    }
}
