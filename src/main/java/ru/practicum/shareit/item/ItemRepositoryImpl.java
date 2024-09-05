package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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
        return null;
    }

    @Override
    public Item findById(long userId, long itemId) {
        return null;
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
