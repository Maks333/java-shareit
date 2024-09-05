package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private static long nextItemId = 1;
    private final UserService service;
    private final Map<Long, Map<Long, Item>> items = new HashMap<>();

    @Override
    public Item create(long userId, ItemDto itemDto) {
        User user = service.findById(userId);
        Item item = ItemMapper.toItem(itemDto);
        long itemId = nextItemId++;
        item.setId(itemId);

        Map<Long, Item> itemMap = new HashMap<>();
        itemMap.put(itemId, item);
        items.put(user.getId(), itemMap);
        return item;
    }

    @Override
    public Item update(long userId, long itemId, ItemDto itemDto) {
        User user = service.findById(userId);
        Map<Long, Item> userItems = items.get(user.getId());
        if (userItems == null) {
            throw new NotFoundException("User with id " + userId + " is not found");
        }
        Item item = userItems.get(itemId);
        if (item == null) {
            throw new NotFoundException("Item with id " + itemId + " is not found");
        }

        if (itemDto.getName() != null) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null) item.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());
        return item;
    }

    @Override
    public Item findById(long userId, long itemId) {
        User user = service.findById(userId);
        Map<Long, Item> userItems = items.get(user.getId());
        if (userItems == null) {
            throw new NotFoundException("User with id " + userId + " is not found");
        }
        Item item = userItems.get(itemId);
        if (item == null) {
            throw new NotFoundException("Item with id " + itemId + " is not found");
        }
        return item;
    }

    @Override
    public List<Item> findAll(long userId) {
        return List.of();
    }

    @Override
    public List<Item> searchAll(String text) {
        return List.of();
    }

}
