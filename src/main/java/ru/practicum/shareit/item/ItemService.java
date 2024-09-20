package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {
    ItemDto create(long userId, @Valid ItemDto itemDto);

    ItemDto update(long userId, long itemId, ItemDto itemDto);

    ItemDto findById(long userId, long itemId);

    List<ItemDtoWithDates> findAll(long userId);

    List<ItemDto> searchAll(String text);

    CommentDto createComment(long userId, long itemId, @Valid CommentCreateDto commentDto);
}
