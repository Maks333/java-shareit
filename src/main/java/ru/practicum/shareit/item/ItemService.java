package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto create(long userId, @Valid ItemDto itemDto);

    Item update(long userId, long itemId, ItemDto itemDto);

    ItemDto findById(long userId, long itemId);

    List<Item> findAll(long userId);

    List<Item> searchAll(String text);
}
