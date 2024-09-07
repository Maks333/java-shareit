package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.UnauthorizedAccessException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final UserRepository repository;
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item create(long userId, Item item) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " is not found"));
        long itemId = nextItemId();
        item.setId(itemId);
        item.setOwner(user);
        items.put(itemId, item);
        return item;
    }

    @Override
    public Item update(long userId, long itemId, Item item) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " is not found"));

        Item itemToUpdate = Optional.ofNullable(items.get(itemId)).
                orElseThrow(() -> new NotFoundException("Item with id " + itemId + " is not found"));

        if (!itemToUpdate.getOwner().equals(user)) {
            throw new UnauthorizedAccessException(
                    "Item with id " + itemId + " does not belong to user with id + " + userId);
        }

        if (item.getName() != null) itemToUpdate.setName(item.getName());
        if (item.getDescription() != null) itemToUpdate.setDescription(item.getDescription());
        if (item.getAvailable() != null) itemToUpdate.setAvailable(item.getAvailable());
        return itemToUpdate;
    }

    @Override
    public Item findById(long userId, long itemId) {
        repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " is not found"));

        return Optional.ofNullable(items.get(itemId)).
                orElseThrow(() -> new NotFoundException("Item with id " + itemId + " is not found"));
    }

    @Override
    public List<Item> findAll(long userId) {
        repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " is not found"));

        return items.values().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .toList();
    }

    @Override
    public List<Item> searchAll(String text) {
        return items.values().stream()
                .filter(item -> item.getDescription().toLowerCase().contains(text) ||
                        item.getName().toLowerCase().contains(text))
                .filter(Item::getAvailable)
                .toList();
    }

    private long nextItemId() {
        long nextId = items.keySet()
                .stream()
                .max(Long::compareTo)
                .orElse(1L);
        return ++nextId;
    }
}
