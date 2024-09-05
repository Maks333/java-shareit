package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryIml implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Optional<User> findById(long userId) {
        return Optional.of(users.get(userId));
    }

    @Override
    public User create(User user) {
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

    private long nextUserId() {
        long nextId = users.keySet()
                .stream()
                .max(Long::compareTo)
                .orElse(1L);
        return ++nextId;
    }
}
