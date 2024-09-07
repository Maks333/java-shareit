package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.Collections;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public ItemDto create(long userId, @Valid ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemRepository.create(userId, item));
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemRepository.update(userId, itemId, item));
    }

    @Override
    public ItemDto findById(long userId, long itemId) {
        return ItemMapper.toItemDto(itemRepository.findById(userId, itemId));
    }

    @Override
    public List<ItemDto> findAll(long userId) {
        return itemRepository.findAll(userId).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> searchAll(String text) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.searchAll(text.toLowerCase()).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }
}
