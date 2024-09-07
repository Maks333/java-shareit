package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item create(long userId, Item item);

    Item update(long userId, long itemId, Item item);

    Item findById(long userId, long itemId);

    List<Item> findAll(long userId);

    List<Item> searchAll(String text);
}
