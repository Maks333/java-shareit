package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithAdditionalInfo;

import java.util.List;

public interface ItemService {
    ItemDto create(long userId, @Valid ItemDto itemDto);

    ItemDto update(long userId, long itemId, ItemDto itemDto);

    ItemDtoWithAdditionalInfo findById(long userId, long itemId);

    List<ItemDtoWithAdditionalInfo> findAll(long userId);

    List<ItemDto> searchAll(String text);

    CommentDto createComment(long userId, long itemId, @Valid CommentCreateDto commentDto);
}
