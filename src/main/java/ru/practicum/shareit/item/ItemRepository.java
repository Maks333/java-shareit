package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item create(long userId, Item itemDto);

    Item update(long userId, long itemId, ItemDto itemDto);

    Item findById(long userId, long itemId);

    List<Item> findAll(long userId);

    List<Item> searchAll(String text);
}
