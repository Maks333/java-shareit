package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.EmailIsNotUniqueException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryIml implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Optional<User> findById(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public User create(User user) {
        //validate uniqueness of email
        if (isEmailNotUnique(user.getEmail())) {
            throw new EmailIsNotUniqueException("Email must be unique");
        }
        //generate new id
        long userId = nextUserId();
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public User update(long userId, User user) {
        return null;
    }

    @Override
    public void delete(long userId) {

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
