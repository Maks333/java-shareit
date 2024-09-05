package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    Map<Long, Map<Long, Item>> items = new HashMap<>();

    @Override
    public Item create(long userId, ItemDto itemDto) {
        return null;
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
